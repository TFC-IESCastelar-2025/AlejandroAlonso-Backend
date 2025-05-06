package com.daw2.fct_bbdd.models.repository;

import com.daw2.fct_bbdd.models.entity.Figure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FigureRepository extends JpaRepository<Figure, Long> {

}