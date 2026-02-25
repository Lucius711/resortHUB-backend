package com.threektechone.resorthub.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.threektechone.resorthub.models.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    
}
