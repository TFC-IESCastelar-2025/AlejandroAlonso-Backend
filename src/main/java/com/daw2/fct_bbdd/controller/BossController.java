package com.daw2.fct_bbdd.controller;

import com.daw2.fct_bbdd.models.dto.BossDTO;
import com.daw2.fct_bbdd.service.BossService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/boss")
public class BossController {

    @Autowired
    BossService bossService;

    @GetMapping("/ver")
    public List<BossDTO> ver() {
        return BossDTO.from(bossService.findAll());
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
    public ResponseEntity<BossDTO> getRandomBossForToday() {
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
}
