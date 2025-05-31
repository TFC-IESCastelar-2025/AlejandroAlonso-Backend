package com.daw2.fct_bbdd.auth.controllers;

import com.daw2.fct_bbdd.auth.models.user.User;
import com.daw2.fct_bbdd.auth.models.user.dto.RankingStreakUserDto;
import com.daw2.fct_bbdd.auth.models.user.dto.RankingUserDto;
import com.daw2.fct_bbdd.auth.models.user.dto.UserDto;
import com.daw2.fct_bbdd.auth.payload.response.MessageResponse;
import com.daw2.fct_bbdd.auth.repository.UserRepository;
import com.daw2.fct_bbdd.auth.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/profile")
public class UserProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

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

    @GetMapping("/streak")
    public ResponseEntity<?> getStreak(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Long streak = user.getStreak();
        LocalDate lastUpdate = user.getLastStreakUpdate();

        if (lastUpdate == null) {
            return ResponseEntity.ok(0);
        }

        LocalDate today = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(lastUpdate, today);

        if (daysBetween >= 2) {
            User existingUser = userService.findByUsername(username);
            existingUser.setStreak(0L);
            userService.save(existingUser);
            return ResponseEntity.ok(0L);
        }

        return ResponseEntity.ok(streak != null ? streak : 0);
    }

    @GetMapping("/add-streak")
    public ResponseEntity<?> updateStreak(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        LocalDate today = LocalDate.now();
        LocalDate lastUpdate = user.getLastStreakUpdate();

        if (lastUpdate != null && lastUpdate.isEqual(today)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Ya has actualizado tu racha hoy.");
        }

        User existingUser = userService.findByUsername(username);

        Long streak = existingUser.getStreak() != null ? existingUser.getStreak() + 1 : 1L;
        existingUser.setStreak(streak);
        existingUser.setLastStreakUpdate(today);

        Long maxStreak = existingUser.getMaxStreak() != null ? existingUser.getMaxStreak() : 0L;
        if (streak > maxStreak) {
            existingUser.setMaxStreak(streak);
        }

        userService.save(existingUser);

        return ResponseEntity.ok(streak);
    }

    @GetMapping("/ranking-streak")
    public List<RankingStreakUserDto> getRankingStreak() {
        return RankingStreakUserDto.from(userService.findAll());
    }

    /**
     * Actualiza los datos del usuario autenticado
     */
    @PutMapping
    public ResponseEntity<?> updateProfile(@Valid @RequestBody User updatedUser, Authentication authentication) {
        String currentUsername = authentication.getName();
        User currentUser = userService.findByUsername(currentUsername);

        if (currentUser == null) {
            return ResponseEntity.notFound().build();
        }

        if (updatedUser.getUsername() != null && !updatedUser.getUsername().equals(currentUser.getUsername()) &&
                userRepository.existsByUsername(updatedUser.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("El nombre de usuario " + updatedUser.getUsername() + " ya existe."));
        }

        if (updatedUser.getEmail() != null && !updatedUser.getEmail().equals(currentUser.getEmail()) &&
                userRepository.existsByEmail(updatedUser.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("El correo " + updatedUser.getEmail() + " ya existe."));
        }

        User updated = userService.updateById(currentUser.getId(), updatedUser);

        if (updated == null) {
            return ResponseEntity.badRequest().build();
        }

        updated.setPassword(null); // Evitar devolver la contraseña
        return ResponseEntity.ok(updated);
    }

}
