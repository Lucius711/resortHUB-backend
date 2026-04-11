package com.threektechone.resorthub.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threektechone.resorthub.enums.AmenityName;
import com.threektechone.resorthub.models.ResortAmenity;

public interface  ResortAmenityRepository extends JpaRepository<ResortAmenity,Integer> {
    Optional<ResortAmenity> findByName(AmenityName name);
}
