package com.hexaware.assetmanagement.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

	private final String secret = "my-very-secure-and-long-jwt-key-hexaware-2024"; // 🔐 Must be 256-bit (32+ chars)
    private final long expiry = 24 * 60 * 60 * 1000; // 1 day

    private final Key key = Keys.hmacShaKeyFor(secret.getBytes());

    // 🔐 Generate JWT
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiry))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 🔍 Extract username from token
    public String extractUsername(String token) {
        return parseToken(token).getBody().getSubject();
    }

    // 🔍 Extract role from token
    public String extractRole(String token) {
        return parseToken(token).getBody().get("role", String.class);
    }

    // ✅ Validate token with username
    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    // ⏳ Token expiry check
    private boolean isTokenExpired(String token) {
        return parseToken(token).getBody().getExpiration().before(new Date());
    }

    // 🔓 Internal parser
    private Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
