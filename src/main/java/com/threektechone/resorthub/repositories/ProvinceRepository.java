package com.threektechone.resorthub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.threektechone.resorthub.models.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {
    boolean existsByName(String name);
}