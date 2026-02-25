package com.threektechone.resorthub.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.threektechone.resorthub.models.Resort;

public interface ResortRepository extends JpaRepository<Resort, Integer> {
    
}
