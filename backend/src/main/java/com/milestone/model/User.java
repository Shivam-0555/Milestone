package com.milestone.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // BCrypt hashed

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles;

    // RPG Stats
    private Integer level = 1;
    private Integer xp = 0;
    private Integer xpToNext = 150;
    private Integer coins = 0;
    private Integer streak = 0;
    private Integer bestStreak = 0;
    
    @Column(name = "class_name")
    private String className = "Novice";
    
    private Integer intelligence = 5;
    private Integer strength = 5;
    private Integer discipline = 5;
    private Integer vitality = 5;
    
    private Integer questsDone = 0;
    private Integer achievementCount = 0;

    private LocalDate lastActiveDate;
}
