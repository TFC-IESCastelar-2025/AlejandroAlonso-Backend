package com.daw2.fct_bbdd.service.impl;

import com.daw2.fct_bbdd.models.entity.Boss;
import com.daw2.fct_bbdd.models.entity.Prueba;
import com.daw2.fct_bbdd.models.repository.BossRepository;
import com.daw2.fct_bbdd.service.BossService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@RestController
public class BossServiceImpl implements BossService {

    @Autowired
    private BossRepository bossRepository;

    private LocalDate lastChangeDate = LocalDate.now();
    private Boss lastRandomBoss = null;

    public List<Boss> findAll() {
        return bossRepository.findAll();
    }

    public Boss getRandomBoss() {
        List<Boss> allBosses = bossRepository.findAll();
        if (allBosses.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(allBosses.size());
        Boss randomBoss = allBosses.get(randomIndex);

        lastRandomBoss = randomBoss;
        return randomBoss;
    }

    public Boss getRandomBossForToday() {
        LocalDate today = LocalDate.now();

        if (lastRandomBoss != null && today.isEqual(lastChangeDate)) {
            return lastRandomBoss;
        }

        List<Boss> allBosses = bossRepository.findAll();
        if (allBosses.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(allBosses.size());
        Boss randomBoss = allBosses.get(randomIndex);
        lastRandomBoss = randomBoss;
        lastChangeDate = today;

        return randomBoss;
    }


}
