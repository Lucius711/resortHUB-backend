package com.threektechone.resorthub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threektechone.resorthub.models.ResortImage;

public interface ResortImageRepository extends JpaRepository<ResortImage, Integer> {
    
}
