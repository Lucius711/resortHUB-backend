package com.threektechone.resorthub.dto.chat;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ConversationResponseDTO {
    private int partnerId;
    private ChatMessageResponseDTO lastMessage;
    private long unreadCount;
    private LocalDateTime lastMessageTimestamp;
}

