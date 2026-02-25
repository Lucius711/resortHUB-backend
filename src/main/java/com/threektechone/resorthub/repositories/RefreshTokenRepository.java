package com.threektechone.resorthub.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.threektechone.resorthub.models.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    
}
