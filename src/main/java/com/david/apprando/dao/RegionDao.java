package com.david.apprando.dao;

import com.david.apprando.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionDao extends JpaRepository<Region, Integer> {
    Region findByNom(String nom);
}
