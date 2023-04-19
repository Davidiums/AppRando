package com.david.apprando.security;
import com.david.apprando.dao.UtilisateurDao;
import com.david.apprando.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class MyUds implements UserDetailsService{
    final UtilisateurDao utilisateurDao;

    public MyUds(UtilisateurDao utilisateurDao) {
        this.utilisateurDao = utilisateurDao;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utilisateur> utilisateur = utilisateurDao.findByEmail(username);

        if(utilisateur.isEmpty()){
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }
        return new MyUserDetails(utilisateur.get());
    }
}
