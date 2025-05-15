package com.daw2.fct_bbdd.auth.services.impl;

import com.daw2.fct_bbdd.auth.models.user.User;
import com.daw2.fct_bbdd.auth.repository.UserRepository;
import com.daw2.fct_bbdd.auth.services.UserService;
import com.daw2.fct_bbdd.models.entity.Boss;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User save(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username){
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Boolean deleteByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if(user == null){
            return false;
        } else{
            userRepository.delete(user);
            return true;
        }
    }

    @Override
    public User updateById(Long id, User updatedUser){
        User existingUser = userRepository.findById(id).orElse(null);
        if(existingUser != null){
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(encoder.encode(updatedUser.getPassword()));
            }
            return userRepository.save(existingUser);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public void addBossToUser(String username, Boss boss) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.getBosses().add(boss);
        // No hace falta llamar a save(user), porque la operación es dentro de una transacción y JPA lo persiste automáticamente
    }

}
