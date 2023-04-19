package com.david.apprando.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Evenement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String nom;

    @Column(length = 2, nullable = false)
    private Integer nbParticipantsMax;

    @ManyToOne
    private Utilisateur organisateur;


    @Column
    @OneToMany
    @JoinTable(name = "evenement_randonnee",
            joinColumns = @JoinColumn(name = "evenement_id"),
            inverseJoinColumns = @JoinColumn(name = "utilisateur_id"))
    private Set<Utilisateur> participants = new HashSet<>();
}
