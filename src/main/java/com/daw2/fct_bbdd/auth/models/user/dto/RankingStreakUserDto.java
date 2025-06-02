package com.daw2.fct_bbdd.auth.models.user.dto;

import com.daw2.fct_bbdd.auth.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RankingStreakUserDto {

    private Long id;
    private String username;
    private Long maxStreak;

    public static RankingStreakUserDto from(User entity){
        RankingStreakUserDto dto = null;
        if(entity != null && entity.getEnabled() && entity.getMaxStreak() != null && entity.getMaxStreak() > 1){
            dto = new RankingStreakUserDto();
            dto.setId(entity.getId());
            dto.setUsername(entity.getUsername());
            dto.setMaxStreak(entity.getMaxStreak());
        }
        return dto;
    }

    public static List<RankingStreakUserDto> from(List<User> list){
        List<RankingStreakUserDto> dtos = null;
        if(list != null){
            dtos = new ArrayList<>();
            for(User user : list){
                dtos.add(from(user));
            }
        }
        return dtos;
    }
}
