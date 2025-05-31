package com.daw2.fct_bbdd.controller;

import com.daw2.fct_bbdd.auth.models.user.User;
import com.daw2.fct_bbdd.auth.services.UserService;
import com.daw2.fct_bbdd.common.utils.BossCache;
import com.daw2.fct_bbdd.models.dto.BossDTO;
import com.daw2.fct_bbdd.models.entity.Boss;
import com.daw2.fct_bbdd.service.BossService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/boss")
public class BossController {

    @Autowired
    BossService bossService;

    @Autowired
    UserService userService;

    @Autowired
    private BossCache bossCache;

    @GetMapping("/ver")
    public List<BossDTO> ver() {
        return BossDTO.from(bossCache.getBosses());
    }

    @GetMapping("/random")
    public ResponseEntity<BossDTO> getRandomBoss() {
        try {
            BossDTO randomBoss = BossDTO.from(bossService.getRandomBoss());
            if (randomBoss == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // No se encontró un jefe
            }
            return new ResponseEntity<>(randomBoss, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error al obtener el jefe aleatorio", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/randomtoday")
    public ResponseEntity<?> getRandomBossForToday(Authentication authentication) {
        try {
            BossDTO randomBoss = BossDTO.from(bossService.getRandomBossForToday());
            if (randomBoss == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // No se encontró un jefe
            }
            return new ResponseEntity<>(randomBoss, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error al obtener el jefe aleatorio", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/random/music")
    public ResponseEntity<Map<String, String>> getRandomBossMusic() {
        try {
            Map<String, String> musicInfo = bossService.getRandomBossMusic();
            if (musicInfo == null || musicInfo.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(musicInfo, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error al obtener la música del jefe aleatorio", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/randomtoday/music")
    public ResponseEntity<Map<String, String>> getRandomBossMusicToday() {
        try {
            Map<String, String> musicInfo = bossService.getRandomBossMusicForToday();
            if (musicInfo == null || musicInfo.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(musicInfo, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error al obtener la música del jefe aleatorio", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/saveboss")
    public ResponseEntity<?> registerCorrectGuess(@RequestParam Long bossId, Authentication authentication) {
        try {
            String username = authentication.getName();
            Boss boss = bossService.findById(bossId)
                    .orElseThrow(() -> new RuntimeException("Boss not found"));

            userService.addBossToUser(username, boss);

            return ResponseEntity.ok("Boss added to user guesses.");
        } catch (Exception e) {
            log.error("Error registering boss guess", e);
            return new ResponseEntity<>("Failed to register guess", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
