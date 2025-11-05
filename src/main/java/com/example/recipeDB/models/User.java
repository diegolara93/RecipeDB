package com.example.recipeDB.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Setter
@Getter
@Entity
@Slf4j
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = {"username", "email"})
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userID;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

}
