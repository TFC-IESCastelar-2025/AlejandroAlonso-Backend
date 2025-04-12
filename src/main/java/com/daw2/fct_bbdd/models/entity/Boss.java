package com.daw2.fct_bbdd.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="Bosses")
public class Boss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String game;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Integer health;

    @Column(nullable = false)
    private Integer souls;

    @Column(nullable = false)
    private String difficulty;

    @Column(nullable = false)
    private String Area;

    @Column(nullable = false)
    private String Height;

}
