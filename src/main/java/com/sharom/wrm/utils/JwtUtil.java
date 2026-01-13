package com.sharom.wrm.utils;

import com.sharom.wrm.entity.User;
import com.sharom.wrm.entity.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
@Component
public class JwtUtil {

    private final String secret = "da34fg4fa32dgd7hf325hfdh4dsf2hs4d6ga0r8e2ht435jjgj5757ffds43y5u637";

    public String generateToken(User user){
        return Jwts.builder()
                .setSubject(user.getName())
                .claim("userType", user.getUserType())
                .claim("locationId", user.getLocationId())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(8, ChronoUnit.HOURS)))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public UserType extractUserType(String token) {
        return UserType.valueOf(extractClaims(token).get("userType", String.class));
    }

    public String extractLocationId(String token) {
        return extractClaims(token).get("locationId", String.class);
    }
}
