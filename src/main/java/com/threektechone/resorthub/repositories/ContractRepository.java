package com.threektechone.resorthub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threektechone.resorthub.models.Contract;


public interface ContractRepository extends JpaRepository<Contract, Integer> {
    
}
