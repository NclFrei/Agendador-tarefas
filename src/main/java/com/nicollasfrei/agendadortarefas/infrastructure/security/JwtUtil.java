package com.nicollasfrei.agendadortarefas.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtUtil {

    // Chave secreta usada para assinar e verificar tokens JWT
    private final Key secretKey;

    public JwtUtil() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(secretKey)      // usa o Key diretamente
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey) // usa o Key diretamente
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extrairEmailToken(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username) {
        String extractedUsername = extrairEmailToken(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }
}
