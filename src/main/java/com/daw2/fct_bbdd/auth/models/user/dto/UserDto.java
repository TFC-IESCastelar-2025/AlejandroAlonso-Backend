package com.daw2.fct_bbdd.auth.models.user.dto;

import com.daw2.fct_bbdd.auth.models.user.User;
import com.daw2.fct_bbdd.models.entity.Boss;
import com.daw2.fct_bbdd.models.entity.Figure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Instant createdAt;
    private Instant updatedAt;
    private List<Boss> bosses;

    public static UserDto from(User entity){
        UserDto dto = null;
        if(entity != null){
            dto = new UserDto();
            dto.setId(entity.getId());
            dto.setUsername(entity.getUsername());
            dto.setEmail(entity.getEmail());
            dto.setPassword(entity.getPassword());
            dto.setCreatedAt(entity.getCreatedAt());
            dto.setUpdatedAt(entity.getUpdatedAt());

            if (entity.getBosses() != null) {
                dto.setBosses(
                    entity.getBosses().stream().collect(Collectors.toList())
                );
            }
        }
        return dto;
    }

    public static List<UserDto> from(List<User> list){
        List<UserDto> dtos = null;
        if(list != null){
            dtos = new ArrayList<>();
            for(User user : list){
                dtos.add(from(user));
            }
        }
        return dtos;
    }

    public User to(){
        User entity = new User();
        entity.setId(id);
        entity.setUsername(username);
        entity.setEmail(email);
        entity.setPassword(password);
//        entity.setCreatedAt(Instant.parse(created_at));
//        entity.setUpdatedAt(Instant.parse(updated_at));
        return entity;
    }

}
