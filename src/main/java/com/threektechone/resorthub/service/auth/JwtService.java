package com.threektechone.resorthub.service.auth;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;


public interface JwtService {
    String generateAccessToken(UserDetails  userDetails);

    String generateRefreshToken(UserDetails  userDetails);

    String extractEmail(String token);

    String extractJti(String token);

    Date extractExpiration(String token);

    boolean isTokenValid(String token, UserDetails  userDetails);

    boolean isTokenExpired(String token);

    Claims extractAllClaims(String token);

  
}
