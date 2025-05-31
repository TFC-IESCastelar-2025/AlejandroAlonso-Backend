package com.daw2.fct_bbdd.auth.models.user;

import com.daw2.fct_bbdd.auth.models.role.Role;
import com.daw2.fct_bbdd.models.entity.Boss;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS",
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "email") 
    })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public User (String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  @Size(max = 20)
  @Column(length = 50, unique = true)
  private String username;

  @Size(max = 50)
  @Email
  @Column(length = 150, unique = true)
  private String email;

  @Column(length = 60)
  private String password;

  @Column
  private Long streak;

  @Column(name = "last_streak_update")
  private LocalDate lastStreakUpdate;

  @Column
  private Long maxStreak;

  @Column
  private Boolean enabled = false;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "USER_ROLES",
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "USER_BOSSES",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "boss_id"))
  private Set<Boss> bosses = new HashSet<>();

  @CreationTimestamp
  private Instant createdAt;
  @UpdateTimestamp
  private Instant updatedAt;
}
