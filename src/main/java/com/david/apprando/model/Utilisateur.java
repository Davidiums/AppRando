package com.david.apprando.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String nom;

    @Column(length = 20)
    private String prenom;

    @Column(length = 64)
    private String email;

    @Column
    private LocalDate dateNaissance;

    @Column
    private String password;

    @ManyToOne
    private Role role;

    @ManyToOne
    private Images photo;
}
