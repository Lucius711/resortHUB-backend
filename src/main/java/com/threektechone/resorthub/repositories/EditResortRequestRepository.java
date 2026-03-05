package com.threektechone.resorthub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threektechone.resorthub.models.EditResortRequest;

public interface EditResortRequestRepository extends JpaRepository<EditResortRequest,Integer > {
    
}
