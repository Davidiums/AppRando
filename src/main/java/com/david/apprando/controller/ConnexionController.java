package com.david.apprando.controller;

import com.david.apprando.dao.UtilisateurDao;
import com.david.apprando.model.Utilisateur;
import com.david.apprando.security.JwtUtils;
import com.david.apprando.security.MyUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@CrossOrigin
public class ConnexionController {

    final AuthenticationManager authenticationManager;
    final UtilisateurDao utilisateurDao;
    final JwtUtils jwtUtils;
    final PasswordEncoder passwordEncoder;

    public ConnexionController(UtilisateurDao utilisateurDao, JwtUtils jwtUtils, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.utilisateurDao = utilisateurDao;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/inscription")
    public ResponseEntity<Utilisateur> inscription(@RequestBody Utilisateur utilisateur){
        System.out.println(utilisateur);
        if(utilisateur.getId() != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (utilisateur.getPassword().length()<=3){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (utilisateur.getEmail() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(utilisateur.getEmail()).matches()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Utilisateur> optional = utilisateurDao.findByEmail(utilisateur.getEmail());
        if (optional.isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String hash = passwordEncoder.encode(utilisateur.getPassword());
        utilisateur.setPassword(hash);
        utilisateurDao.save(utilisateur);
        return new ResponseEntity<>(utilisateur, HttpStatus.CREATED);
    }

    @PostMapping("/connexion")
    public ResponseEntity<String> connexion(@RequestBody Utilisateur utilisateur){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(utilisateur.getEmail(), utilisateur.getPassword())
            );
        }catch (BadCredentialsException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(jwtUtils.generateJwt(new MyUserDetails(utilisateur)), HttpStatus.OK);
    }
}
