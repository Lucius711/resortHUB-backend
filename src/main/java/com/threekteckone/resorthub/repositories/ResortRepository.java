package com.threekteckone.resorthub.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.threekteckone.resorthub.models.Resort;

public interface ResortRepository extends JpaRepository<Resort, Integer> {
    
}
