package com.david.apprando.dao;

import com.david.apprando.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.awt.*;

public interface ImagesDao extends JpaRepository<Images, Integer> {
    Image findByNom(String nom);
}
