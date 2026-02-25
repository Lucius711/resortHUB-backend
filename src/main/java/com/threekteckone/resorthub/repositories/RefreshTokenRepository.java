package com.threekteckone.resorthub.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.threekteckone.resorthub.models.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    
}
