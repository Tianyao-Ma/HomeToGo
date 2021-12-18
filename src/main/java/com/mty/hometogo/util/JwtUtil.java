package com.mty.hometogo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class JwtUtil {
    // inject variable from application.properties
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String subject) {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
    // claims is about payloads in jwt
    private Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJwt(token).getBody();
    }
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }
    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }
    public Boolean validateToken(String token) {
        return extractExpiration(token).after(new Date());
    }
}
