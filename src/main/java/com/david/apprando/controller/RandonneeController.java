package com.david.apprando.controller;

import com.david.apprando.dao.RandonneeDao;
import com.david.apprando.dao.RegionDao;
import com.david.apprando.model.Images;
import com.david.apprando.model.Randonnee;
import com.david.apprando.model.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin
public class RandonneeController {

    final RandonneeDao randonneeDao;
    final RegionDao regionDao;

    public RandonneeController(RandonneeDao randonneeDao, RegionDao regionDao) {
        this.randonneeDao = randonneeDao;
        this.regionDao = regionDao;
    }

    @PostMapping("/randonnees")
    public ResponseEntity<?> addRandonnee(@RequestBody Randonnee randonnee) throws Exception{

        //vérification des entrées utilisateurs et de l'unicité de la randonnée
        Optional<Randonnee> doesRandonneeExist = randonneeDao.findByNom(randonnee.getNom());
        Optional<Region> region = regionDao.findById(randonnee.getRegion().getId());
        if (region.isEmpty()){
            return ResponseEntity.status(409).body("La région " + randonnee.getRegion().getNom() + " n'existe pas");
        }

        if (doesRandonneeExist.isPresent()){
            if(doesRandonneeExist.get().getRegion().equals(randonnee.getRegion())){
                String message = "La randonnée " + randonnee.getNom() + " existe déjà dans la région " + randonnee.getRegion();
                return ResponseEntity.status(409).body(message);
            }
        }
        if (randonnee.getNom().equals("")){
            return ResponseEntity.status(400).body("Le nom de la randonnée ne peut pas être vide");
        }
        if (randonnee.getDistance() < 0){
            return ResponseEntity.status(400).body("La distance ne peut pas être négative");
        }
        Long duree = randonnee.getDuree();

        if(randonnee.getDenivele() <0){
            return ResponseEntity.status(400).body("Le dénivelé ne peut pas être négatif");
        }
        //correction des durée fantaisistes, si la durée est inferieur à 1h pour 4km avec 1m de dénivelé = 6m de plat
        if(duree < (randonnee.getDistance() + randonnee.getDenivele()*6) / 4 ){
            duree = (randonnee.getDistance() + randonnee.getDenivele()*6) / 4;
        }
        if(randonnee.getDenivele() > randonnee.getDistance()){
            return ResponseEntity.status(400).body("Le dénivelé ne peut pas être supérieur à la distance");
        }

        //création de la randonnée
        Randonnee randonneeToSave = new Randonnee();
        randonneeToSave.setNom(randonnee.getNom());
        randonneeToSave.setRegion(region.get());
        randonneeToSave.setDuree(duree);
        randonneeToSave.setDistance(randonnee.getDistance());
        randonneeToSave.setDenivele(randonnee.getDenivele());
        randonneeToSave.setDescription(randonnee.getDescription());
//        randonneeToSave.setImages(randonnee.getImages()); pas de traitement d'image pour le moment

        randonneeDao.save(randonneeToSave);
        return ResponseEntity.status(201).body(randonneeToSave);
    }

}
