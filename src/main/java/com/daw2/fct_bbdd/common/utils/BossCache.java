package com.daw2.fct_bbdd.common.utils;

import com.daw2.fct_bbdd.models.entity.Boss;
import com.daw2.fct_bbdd.service.BossService;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class BossCache {

    private final BossService bossService;

    @Getter
    private List<Boss> bosses;

    public BossCache(BossService bossService) {
        this.bossService = bossService;
    }

    @PostConstruct
    public void init() {
        log.info("Cargando bosses en memoria...");
        this.bosses = bossService.findAll();
        log.info("Se han cargado {} bosses en memoria", bosses.size());
    }

    public void recargar() {
        log.info("Recargando bosses en memoria...");
        this.bosses = bossService.findAll();
        log.info("Se han recargado {} bosses en memoria", bosses.size());
    }
}
