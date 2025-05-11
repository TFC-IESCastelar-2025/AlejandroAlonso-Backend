package com.daw2.fct_bbdd.models.dto;

import com.daw2.fct_bbdd.models.entity.Boss;
import com.daw2.fct_bbdd.models.entity.Figure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BossDTO {

    private Long id;
    private String image;
    private String game;
    private String name;
    private Integer health;
    private Integer souls;
    private String difficulty;
    private String area;
    private String height;
    private String music;
    private List<String> figures;

    public static BossDTO from(Boss entity) {
        BossDTO dto = null;
        if (entity != null) {
            dto = new BossDTO();
            dto.setId(entity.getId());
            dto.setImage(entity.getImage());
            dto.setGame(entity.getGame());
            dto.setName(entity.getName());
            dto.setHealth(entity.getHealth());
            dto.setSouls(entity.getSouls());
            dto.setDifficulty(entity.getDifficulty());
            dto.setArea(entity.getArea());
            dto.setHeight(entity.getHeight());
            dto.setMusic(entity.getMusic());
            if (entity.getFigures() != null) {
                dto.setFigures(
                        entity.getFigures().stream()
                                .map(Figure::getName)
                                .collect(Collectors.toList())
                );
            }
        }
        return dto;
    }

    public static List<BossDTO> from(List<Boss> list) {
        List<BossDTO> dtos = null;
        if (list != null) {
            dtos = new ArrayList<>();
            for (Boss boss : list) {
                dtos.add(from(boss));
            }
        }
        return dtos;
    }

    public Boss to() {
        Boss entity = new Boss();
        entity.setId(id);
        entity.setImage(image);
        entity.setGame(game);
        entity.setName(name);
        entity.setHealth(health);
        entity.setSouls(souls);
        entity.setDifficulty(difficulty);
        entity.setArea(area);
        entity.setHeight(height);
        entity.setMusic(music);
        return entity;
    }
}
