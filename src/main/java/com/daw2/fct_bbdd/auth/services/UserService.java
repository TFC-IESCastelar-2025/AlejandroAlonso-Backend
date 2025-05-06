package com.daw2.fct_bbdd.auth.services;

import com.daw2.fct_bbdd.auth.models.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);

    List<User> findAll();

    User findByUsername(String username);

    Boolean deleteByUsername(String username);

    User updateById(Long id, User updatedUser);
}
