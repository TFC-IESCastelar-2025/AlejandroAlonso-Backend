package com.daw2.fct_bbdd.models.repository;

import com.daw2.fct_bbdd.models.entity.Prueba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PruebaRepository extends JpaRepository<Prueba, Long> {



}
