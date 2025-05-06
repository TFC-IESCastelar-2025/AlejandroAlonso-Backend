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
public class CreateUserDto {

    private String username;
    private String email;
    private String password;

    public static UserDto from(User entity){
        UserDto dto = null;
        if(entity != null){
            dto = new UserDto();
            dto.setUsername(entity.getUsername());
            dto.setEmail(entity.getEmail());
            dto.setPassword(entity.getPassword());
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
        entity.setUsername(username);
        entity.setEmail(email);
        entity.setPassword(password);
        return entity;
    }

}
