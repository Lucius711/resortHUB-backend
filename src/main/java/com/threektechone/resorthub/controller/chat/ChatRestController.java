package com.threektechone.resorthub.controller.chat;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.threektechone.resorthub.common.response.ApiResponse;
import com.threektechone.resorthub.dto.chat.ChatMessageResponseDTO;
import com.threektechone.resorthub.dto.chat.ConversationResponseDTO;
import com.threektechone.resorthub.service.chat.ChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class ChatRestController {

    private final ChatService chatService;

    @GetMapping("/api/messages/{userId}")
    public ResponseEntity<ApiResponse<List<ChatMessageResponseDTO>>> getChatHistory(
            @PathVariable int userId,
            Authentication authentication) {
        String viewerEmail = authentication.getName();
        List<ChatMessageResponseDTO> history = chatService.getChatHistory(viewerEmail, userId);
        return ResponseEntity.ok(new ApiResponse<>(200, null, history, LocalDateTime.now()));
    }

    @GetMapping("/api/conversations")
    public ResponseEntity<ApiResponse<List<ConversationResponseDTO>>> getConversations(Authentication authentication) {
        String viewerEmail = authentication.getName();
        List<ConversationResponseDTO> conversations = chatService.listConversations(viewerEmail);
        return ResponseEntity.ok(new ApiResponse<>(200, null, conversations, LocalDateTime.now()));
    }
}

