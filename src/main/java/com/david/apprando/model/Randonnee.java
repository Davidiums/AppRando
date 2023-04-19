package com.david.apprando.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
public class Randonnee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String nom;

    @Column
    private Long distance;

    @Column
    private Integer denivele;

    @Column
    private String description;

    @Column
    private Long duree;


    @OneToMany
    @JoinTable(name = "images_randonnee",
            joinColumns = @JoinColumn(name = "randonnee_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<Images> images = new HashSet<>();


    @ManyToOne
    private Region region;
}
