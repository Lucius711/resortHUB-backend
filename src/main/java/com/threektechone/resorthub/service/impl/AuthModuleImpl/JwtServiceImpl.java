package com.threektechone.resorthub.service.impl.AuthModuleImpl;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.threektechone.resorthub.config.properties.JwtProperties;
import com.threektechone.resorthub.service.AuthModule.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtProperties jwtProperties;

    private SecretKey  getSigningKey() {
        byte[] keyBytes = jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    //Generate Token
    @Override
    public String generateAccessToken(UserDetails userDetails) {
        // Implementation for generating JWT token based on user details
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(getSigningKey())
                .compact();
    }
    
    //Generate Refresh Token
    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        // Implementation for generating refresh JWT token based on user details
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshExpiration()))
                .signWith(getSigningKey())
                .compact();
    }
    
    //Extract Email from Token
    @Override
    public String extractEmail(String token) {
        // Implementation for extracting email from JWT token
        return extractAllClaims(token).getSubject(); // Placeholder return statement
    }
    

    //Extract Expiration Date from Token
    @Override
    public Date extractExpiration(String token) {
        // Implementation for extracting expiration date from JWT token
        return extractAllClaims(token).getExpiration(); // Placeholder return statement
    }
    
    //Validate Token
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        // Implementation for validating JWT token against user details
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    

    //Check if Token is Expired
    @Override
    public boolean isTokenExpired(String token) {
        // Implementation for checking if JWT token is expired
        return extractExpiration(token).before(new Date()); // Placeholder return statement
    }
    
    //Extract All Claims from Token
    @Override
    public Claims extractAllClaims(String token) {
        // Implementation for extracting all claims from JWT token
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
}