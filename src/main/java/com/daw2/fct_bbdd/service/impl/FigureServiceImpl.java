package com.daw2.fct_bbdd.service.impl;

import com.daw2.fct_bbdd.models.entity.Figure;
import com.daw2.fct_bbdd.models.repository.FigureRepository;
import com.daw2.fct_bbdd.service.FigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class FigureServiceImpl implements FigureService {

    @Autowired
    private FigureRepository figureRepository;

    public List<Figure> findAll() {
        return figureRepository.findAll();
    }
}