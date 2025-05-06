package com.daw2.fct_bbdd.auth.repository;

import com.daw2.fct_bbdd.auth.models.validCode.ValidCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidCodeRepository extends JpaRepository<ValidCode, Long> {
    Optional<ValidCode> findByCode(String code);
}
