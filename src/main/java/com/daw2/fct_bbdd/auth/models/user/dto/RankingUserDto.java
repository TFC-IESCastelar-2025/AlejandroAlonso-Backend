package com.daw2.fct_bbdd.auth.models.user.dto;

import com.daw2.fct_bbdd.auth.models.user.User;
import com.daw2.fct_bbdd.models.dto.BossDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RankingUserDto {

    private Long id;
    private String username;
    private Integer bossesCount;

    public static RankingUserDto from(User entity){
        RankingUserDto dto = null;
        if(entity != null && entity.getEnabled() && !entity.getBosses().isEmpty()){
            dto = new RankingUserDto();
            dto.setId(entity.getId());
            dto.setUsername(entity.getUsername());
            if (entity.getBosses() != null) {
                dto.setBossesCount(entity.getBosses().size());
            }
        }
        return dto;
    }

    public static List<RankingUserDto> from(List<User> list){
        List<RankingUserDto> dtos = null;
        if(list != null){
            dtos = new ArrayList<>();
            for(User user : list){
                dtos.add(from(user));
            }
        }
        return dtos;
    }
}
