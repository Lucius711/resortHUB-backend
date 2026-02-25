package com.threektechone.resorthub.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.threektechone.resorthub.models.LostFoundItem;

public interface LostFoundItemRepository extends JpaRepository<LostFoundItem, Integer> {
    
}
