package com.threektechone.resorthub.config.websocket;

import java.time.LocalDateTime;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.threektechone.resorthub.dto.chat.UserPresenceEventDTO;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.repositories.UserRepository;
import com.threektechone.resorthub.service.chat.ChatPresenceService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatPresenceEventListener {

    private static final String USER_STATUS_DESTINATION = "/topic/user-status";

    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;
    private final ChatPresenceService presenceService;

    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event) {
        if (event.getUser() == null) {
            return;
        }

        String email = event.getUser().getName();
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return;
        }

        presenceService.markOnline(user.getUserId());

        messagingTemplate.convertAndSend(USER_STATUS_DESTINATION,
                new UserPresenceEventDTO(user.getUserId(), true, LocalDateTime.now()));
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        if (event.getUser() == null) {
            return;
        }

        String email = event.getUser().getName();
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return;
        }

        presenceService.markOffline(user.getUserId());

        messagingTemplate.convertAndSend(USER_STATUS_DESTINATION,
                new UserPresenceEventDTO(user.getUserId(), false, LocalDateTime.now()));
    }
}

