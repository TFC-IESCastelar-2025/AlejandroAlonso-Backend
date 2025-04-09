package com.daw2.fct_bbdd.models.dto;

import com.daw2.fct_bbdd.models.entity.Prueba;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PruebaDTO {
    private Long id;
    private String prueba;


    public static PruebaDTO from(Prueba entity)
    {
        PruebaDTO dto = null;
        if(entity != null)
        {
            dto = new PruebaDTO();
            dto.setId(entity.getId());
            dto.setPrueba(entity.getPrueba());
        }
        return dto;
    }

    public static List<PruebaDTO> from(List<Prueba> list) {
        List<PruebaDTO> dtos = null;
        if (list != null) {
            dtos = new ArrayList<>();
            for (Prueba prueba : list) {
                dtos.add(from(prueba));
            }
        }
        return dtos;
    }

    public Prueba to()
    {
        Prueba entity = new Prueba();
        entity.setId(id);
        entity.setPrueba(prueba);
        return entity;
    }

}
