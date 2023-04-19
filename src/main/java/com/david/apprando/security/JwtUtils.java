package com.david.apprando.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtUtils {

    private static final String SECRET_KEY = "test";
//            System.getenv("JWT_SECRET_KEY");
    public String generateJwt(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Claims getData(String jwt){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(jwt)
                .getBody();
    }

    public boolean isTokenValid(String jwt){
        try {
            Claims donnees = getData(jwt);
        }catch (SignatureException e){
            return false;
        }
        return true;
    }
}
