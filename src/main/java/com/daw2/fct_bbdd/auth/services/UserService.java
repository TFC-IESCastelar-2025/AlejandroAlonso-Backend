package com.daw2.fct_bbdd.auth.services;

import com.daw2.fct_bbdd.auth.models.user.User;
import com.daw2.fct_bbdd.models.entity.Boss;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);

    List<User> findAll();

    User findByUsername(String username);

    Boolean deleteByUsername(String username);

    Boolean deleteByTokenUserId(Long Userid);

    User updateById(Long id, User updatedUser);

    void addBossToUser(String username, Boss boss);
}
