package com.threektechone.resorthub.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.threektechone.resorthub.models.Ward;

@Repository
public interface WardRepository extends JpaRepository<Ward, Integer> {

    List<Ward> findByProvinceId(Integer provinceId);

    boolean existsByNameAndProvince_Code(String name, String code);
}