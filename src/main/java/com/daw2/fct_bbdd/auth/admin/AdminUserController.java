package com.daw2.fct_bbdd.auth.admin;

import com.daw2.fct_bbdd.auth.models.user.User;
import com.daw2.fct_bbdd.auth.models.user.dto.CreateUserDto;
import com.daw2.fct_bbdd.auth.models.user.dto.UserDto;
import com.daw2.fct_bbdd.auth.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/user")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    private List<UserDto> allUser(){
        return UserDto.from(userService.findAll());
    }

    @PostMapping("/find")
    private ResponseEntity<?> findUser(@RequestBody User username){
        try {
            User user = userService.findByUsername(username.getUsername());
            if (user == null){
                throw new RuntimeException();
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(UserDto.from(user));
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping("/create")
    private ResponseEntity<?> createUser(@RequestBody UserDto user){
        try {
            User userSave = user.to();
            userSave.setCreatedAt(Instant.now());
            userSave.setUpdatedAt(Instant.now());
            userService.save(userSave);
            return ResponseEntity.status(HttpStatus.OK).body(CreateUserDto.from(userSave));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error saving user");
        }
    }

    @DeleteMapping("/delete")
    private ResponseEntity<?> deleteUser(@RequestBody User user){
        try {
            Boolean deletedUser = userService.deleteByUsername(user.getUsername());
            if (!deletedUser){
                throw new RuntimeException();
            } else {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PutMapping("/update/{id}")
    private ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user){
        try {
            User updatedUser = userService.updateById(id, user);
            if (updatedUser == null){
                throw new RuntimeException();
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(UserDto.from(updatedUser));
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
