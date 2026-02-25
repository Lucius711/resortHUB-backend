package com.threekteckone.resorthub.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.threekteckone.resorthub.models.LostFoundItem;

public interface LostFoundItemRepository extends JpaRepository<LostFoundItem, Integer> {
    
}
