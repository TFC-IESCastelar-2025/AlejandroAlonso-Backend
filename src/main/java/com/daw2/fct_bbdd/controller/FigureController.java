package com.daw2.fct_bbdd.controller;

import com.daw2.fct_bbdd.models.dto.FigureDTO;
import com.daw2.fct_bbdd.service.FigureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/figure")
public class FigureController {

    @Autowired
    FigureService figureService;

    @GetMapping("/ver")
    public List<FigureDTO> ver() {
        List<FigureDTO> lista = FigureDTO.from(figureService.findAll());
        for (FigureDTO dto : lista) {
            System.out.println("FigureDTO: " + dto.getName() + " - Bosses: " + dto.getBosses());
        }
        return lista;
    }
}