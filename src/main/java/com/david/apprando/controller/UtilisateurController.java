package com.david.apprando.controller;

import com.david.apprando.dao.UtilisateurDao;
import com.david.apprando.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class UtilisateurController {

    private final UtilisateurDao utilisateurDao;


    public UtilisateurController(UtilisateurDao utilisateurDao) {
        this.utilisateurDao = utilisateurDao;
    }

    @PostMapping("/utilisateurs")
    public List<Utilisateur> getUtilisateurs() {
        return utilisateurDao.findAll();
    }

}
