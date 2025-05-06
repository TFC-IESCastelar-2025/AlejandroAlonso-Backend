package com.daw2.fct_bbdd.auth.repository;

import com.daw2.fct_bbdd.auth.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  User findUserByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
