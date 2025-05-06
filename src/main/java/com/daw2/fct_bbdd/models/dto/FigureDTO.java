package com.daw2.fct_bbdd.models.dto;

import com.daw2.fct_bbdd.models.entity.Boss;
import com.daw2.fct_bbdd.models.entity.Figure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FigureDTO {

    private Long id;
    private String name;
    private List<String> bosses;

    public static FigureDTO from(Figure entity) {
        FigureDTO dto = new FigureDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        if (entity.getBosses() != null) {
            dto.setBosses(
                    entity.getBosses()
                            .stream()
                            .map(Boss::getName)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public static List<FigureDTO> from(List<Figure> figures) {
        return figures.stream()
                .map(FigureDTO::from)
                .collect(Collectors.toList());
    }

    public Figure to() {
        Figure figure = new Figure();
        figure.setId(id);
        figure.setName(name);
        return figure;
    }
}
