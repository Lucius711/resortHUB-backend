package com.threektechone.resorthub.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.threektechone.resorthub.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    
}
