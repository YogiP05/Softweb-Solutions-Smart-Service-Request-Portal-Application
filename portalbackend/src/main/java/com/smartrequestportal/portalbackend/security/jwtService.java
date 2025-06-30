package com.smartrequestportal.portalbackend.security;

import io.jsonwebtoken.JwtBuilder;
import org.springframework.stereotype.Component;
import com.smartrequestportal.portalbackend.User.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class jwtService {
    private static final String SECRET = "secret";

    public String generateToken(User user) {
        long now = System.currentTimeMillis();
        long expiresAt = now + 1000 * 60 * 60 * 24;

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expiresAt))
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
                .compact();
    }
}
