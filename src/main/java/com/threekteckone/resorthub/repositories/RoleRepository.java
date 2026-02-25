package com.threekteckone.resorthub.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.threekteckone.resorthub.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    
}
