package com.daw2.fct_bbdd.service.impl;

import com.daw2.fct_bbdd.models.entity.Prueba;
import com.daw2.fct_bbdd.models.repository.PruebaRepository;
import com.daw2.fct_bbdd.service.PruebaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PruebaServiceImpl implements PruebaService {

    @Autowired
    private PruebaRepository pruebaRepository;

    @Transactional
    public Prueba save(Prueba prueba) {
        return pruebaRepository.save(prueba);
    }

    public List<Prueba> findAll() {
        return pruebaRepository.findAll();
    }

}
