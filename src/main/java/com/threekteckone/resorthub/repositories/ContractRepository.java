package com.threekteckone.resorthub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threekteckone.resorthub.models.Contract;


public interface ContractRepository extends JpaRepository<Contract, Integer> {
    
}
