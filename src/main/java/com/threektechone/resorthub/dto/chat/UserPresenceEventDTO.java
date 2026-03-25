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
public class UserPresenceEventDTO {
    private int userId;
    private boolean online;
    private LocalDateTime timestamp;
}

