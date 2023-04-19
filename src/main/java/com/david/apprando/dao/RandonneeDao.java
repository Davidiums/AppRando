package com.david.apprando.dao;

import com.david.apprando.model.Randonnee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RandonneeDao  extends JpaRepository<Randonnee, Integer> {

    Optional<Randonnee> findByNom(String nom);
}
