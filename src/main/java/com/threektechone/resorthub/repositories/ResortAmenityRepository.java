package com.threektechone.resorthub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threektechone.resorthub.models.ResortAmenity;

public interface  ResortAmenityRepository extends JpaRepository<ResortAmenity,Integer> {
    
}
