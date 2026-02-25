package com.threekteckone.resorthub.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.threekteckone.resorthub.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
 
}
