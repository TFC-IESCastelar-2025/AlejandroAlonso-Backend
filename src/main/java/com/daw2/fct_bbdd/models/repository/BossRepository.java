package com.daw2.fct_bbdd.models.repository;

import com.daw2.fct_bbdd.models.entity.Boss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BossRepository extends JpaRepository<Boss, Long> {

}
