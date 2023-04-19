package com.david.apprando.dao;

import com.david.apprando.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurDao extends JpaRepository<Utilisateur, Integer> {
    Utilisateur findByPrenom(String prenom);

    Optional<Utilisateur> findByEmail(String email);
}