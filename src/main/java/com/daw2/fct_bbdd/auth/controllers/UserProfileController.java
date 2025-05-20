package com.daw2.fct_bbdd.auth.controllers;

import com.daw2.fct_bbdd.auth.models.user.User;
import com.daw2.fct_bbdd.auth.models.user.dto.RankingStreakUserDto;
import com.daw2.fct_bbdd.auth.models.user.dto.RankingUserDto;
import com.daw2.fct_bbdd.auth.models.user.dto.UserDto;
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
            existingUser.setLastStreakUpdate(today);
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

        updated.setPassword(null);
        return ResponseEntity.ok(updated);
    }
}
