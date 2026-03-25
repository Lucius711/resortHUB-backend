package com.threektechone.resorthub.controller.chat;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import com.threektechone.resorthub.common.exception.custom.UnauthorizedException;
import com.threektechone.resorthub.dto.chat.ChatSeenMessageRequestDTO;
import com.threektechone.resorthub.dto.chat.ChatSendMessageRequestDTO;
import com.threektechone.resorthub.dto.chat.ChatTypingRequestDTO;
import com.threektechone.resorthub.service.chat.ChatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatSendMessageRequestDTO request, Principal principal) {
        if (principal == null) {
            throw new UnauthorizedException("Missing authentication");
        }
        chatService.sendMessage(principal.getName(), request);
    }

    @MessageMapping("/chat.seen")
    public void markMessageAsRead(@Payload ChatSeenMessageRequestDTO request, Principal principal) {
        if (principal == null) {
            throw new UnauthorizedException("Missing authentication");
        }
        chatService.markMessageAsRead(principal.getName(), request);
    }

    @MessageMapping("/chat.typing")
    public void typing(@Payload ChatTypingRequestDTO request, Principal principal) {
        if (principal == null) {
            throw new UnauthorizedException("Missing authentication");
        }
        chatService.sendTypingIndicator(principal.getName(), request);
    }
}

