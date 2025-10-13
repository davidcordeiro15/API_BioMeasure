package com.API_BioMeasure.Project.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import java.security.Key;
import java.util.Date;

public class JwtUtil {



    private static final String secretKey = System.getenv("SECRET_KEY"); ;

    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hora

    //private static final Key key = Keys.hmacShaKeyFor(secretKey.getBytes());


    // Gera o token JWT com o email do usuário
    public static String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    // Valida o token e retorna o email do usuário
    public static String validateToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}