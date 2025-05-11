package com.daw2.fct_bbdd.service.impl;

import com.daw2.fct_bbdd.models.entity.Boss;
import com.daw2.fct_bbdd.models.repository.BossRepository;
import com.daw2.fct_bbdd.service.BossService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class BossServiceImpl implements BossService {

    @Autowired
    private BossRepository bossRepository;

    private LocalDate lastChangeDate = LocalDate.now();
    private Boss lastRandomBoss = null;
    private Boss lastRandomMusicBoss = null;

    @Transactional
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

    @Transactional
    @Override
    public Map<String, String> getRandomBossMusic() {
        List<Boss> allBosses = bossRepository.findAll();
        if (allBosses.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(allBosses.size());
        Boss randomBoss = allBosses.get(randomIndex);

        Map<String, String> musicInfo = new HashMap<>();
        musicInfo.put("name", randomBoss.getName());
        musicInfo.put("music", randomBoss.getMusic());

        return musicInfo;
    }

    @Override
    public Map<String, String> getRandomBossMusicForToday() {
        LocalDate today = LocalDate.now();

        if (lastRandomMusicBoss != null && today.isEqual(lastChangeDate)) {
            Map<String, String> musicInfo = new HashMap<>();
            musicInfo.put("name", lastRandomMusicBoss.getName());
            musicInfo.put("music", lastRandomMusicBoss.getMusic());
            return musicInfo;
        }

        List<Boss> allBosses = bossRepository.findAll();
        if (allBosses.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(allBosses.size());
        lastRandomMusicBoss = allBosses.get(randomIndex);
        lastChangeDate = today;

        Map<String, String> musicInfo = new HashMap<>();
        musicInfo.put("name", lastRandomMusicBoss.getName());
        musicInfo.put("music", lastRandomMusicBoss.getMusic());

        return musicInfo;
    }
}
