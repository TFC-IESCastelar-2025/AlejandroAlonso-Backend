package com.daw2.fct_bbdd.auth.controllers;

import com.daw2.fct_bbdd.auth.models.user.User;
import com.daw2.fct_bbdd.auth.models.user.dto.UserDto;
import com.daw2.fct_bbdd.auth.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class UserProfileController {

    @Autowired
    private UserService userService;

    /**
     * Devuelve los datos del usuario autenticado
     */
    @GetMapping
    public ResponseEntity<?> getProfile(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(UserDto.from(user));
    }

    /**
     * Actualiza los datos del usuario autenticado
     */
    @PutMapping
    public ResponseEntity<User> updateProfile(@Valid @RequestBody User updatedUser, Authentication authentication) {
        String username = authentication.getName();
        User existingUser = userService.findByUsername(username);

        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        User updated = userService.updateById(existingUser.getId(), updatedUser);

        if (updated == null) {
            return ResponseEntity.badRequest().build();
        }

        updated.setPassword(null); // No devolvemos la contraseña
        return ResponseEntity.ok(updated);
    }
}
