package com.threektechone.resorthub.service.chat;

import java.util.List;

import com.threektechone.resorthub.dto.chat.ChatMessageResponseDTO;
import com.threektechone.resorthub.dto.chat.ChatSeenMessageRequestDTO;
import com.threektechone.resorthub.dto.chat.ChatSendMessageRequestDTO;
import com.threektechone.resorthub.dto.chat.ChatTypingRequestDTO;
import com.threektechone.resorthub.dto.chat.ConversationResponseDTO;

public interface ChatService {
    ChatMessageResponseDTO sendMessage(String senderEmail, ChatSendMessageRequestDTO request);

    void markMessageAsRead(String receiverEmail, ChatSeenMessageRequestDTO request);

    void sendTypingIndicator(String senderEmail, ChatTypingRequestDTO request);

    List<ChatMessageResponseDTO> getChatHistory(String userEmail, int otherUserId);

    List<ConversationResponseDTO> listConversations(String userEmail);
}

