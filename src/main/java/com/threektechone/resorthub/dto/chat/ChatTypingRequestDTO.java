package com.threektechone.resorthub.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatTypingRequestDTO {
    private int receiverId;
    private boolean typing;
}

