package com.david.apprando.dao;

import com.david.apprando.model.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvenementDao  extends JpaRepository<Evenement, Integer> {

        Evenement findByNom(String nom);
}
