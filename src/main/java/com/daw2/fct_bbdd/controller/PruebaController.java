package com.daw2.fct_bbdd.controller;

import com.daw2.fct_bbdd.models.dto.PruebaDTO;
import com.daw2.fct_bbdd.models.entity.Prueba;
import com.daw2.fct_bbdd.service.PruebaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/prueba")
public class PruebaController {

    @Autowired
    PruebaService pruebaService;

    @GetMapping("/ver")
    public List<PruebaDTO> ver() {
        return PruebaDTO.from(pruebaService.findAll());
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody PruebaDTO pruebaDto) {
        try {
            Prueba prueba = pruebaDto.to();
            pruebaService.save(prueba);
            return ResponseEntity.status(HttpStatus.OK).body(PruebaDTO.from(prueba));

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Prueba no guardada");
        }
    }

}
