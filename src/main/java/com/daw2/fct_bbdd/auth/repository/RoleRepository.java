package com.daw2.fct_bbdd.auth.repository;

import com.daw2.fct_bbdd.auth.models.role.Role;
import com.daw2.fct_bbdd.auth.models.role.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(RoleEnum name);
}
