package com.threektechone.resorthub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threektechone.resorthub.models.ResortMenu;

public interface ResortMenuRepository extends JpaRepository<ResortMenu, Integer>{
    
}
