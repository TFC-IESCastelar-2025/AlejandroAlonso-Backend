package com.daw2.fct_bbdd.service;


import com.daw2.fct_bbdd.models.entity.Boss;
import com.daw2.fct_bbdd.models.entity.Prueba;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface BossService {

    List<Boss> findAll();

    Optional<Boss> findById(Long id);

    Boss getRandomBoss();

    Boss getRandomBossForToday();

    Map<String, String> getRandomBossMusic();

    Map<String, String> getRandomBossMusicForToday();
}
