package com.daw2.fct_bbdd.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="Bosses")
public class Boss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(unique = true, nullable = false, columnDefinition = "LONGTEXT")
    private String image;

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
    private String area;

    @Column(nullable = false)
    private String height;

    @Lob
    @Column(unique = true, nullable = false, columnDefinition = "LONGTEXT")
    private String music;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "boss_figure",
            joinColumns = @JoinColumn(name = "boss_id"),
            inverseJoinColumns = @JoinColumn(name = "figure_id")
    )
    private List<Figure> figures = new ArrayList<>();
}
