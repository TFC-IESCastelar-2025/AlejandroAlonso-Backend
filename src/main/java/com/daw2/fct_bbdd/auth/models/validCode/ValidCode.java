package com.daw2.fct_bbdd.auth.models.validCode;

import com.daw2.fct_bbdd.auth.models.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "VALID_CODE")
public class ValidCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String code;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
