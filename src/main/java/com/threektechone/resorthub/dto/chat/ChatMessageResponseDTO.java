package com.threektechone.resorthub.dto.chat;

import java.time.LocalDateTime;

import com.threektechone.resorthub.enums.ChatMessageStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatMessageResponseDTO {
    private int id;
    private int senderId;
    private int receiverId;
    private String content;
    private LocalDateTime timestamp;
    private ChatMessageStatus status;
}

