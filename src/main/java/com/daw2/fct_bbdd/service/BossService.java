package com.daw2.fct_bbdd.service;


import com.daw2.fct_bbdd.models.entity.Boss;
import com.daw2.fct_bbdd.models.entity.Prueba;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BossService {

    List<Boss> findAll();

    Boss getRandomBoss();

    Boss getRandomBossForToday();
}
