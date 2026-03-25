package com.threektechone.resorthub.service.impl.chat;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.threektechone.resorthub.common.exception.custom.UnauthorizedException;
import com.threektechone.resorthub.dto.chat.ChatMessageResponseDTO;
import com.threektechone.resorthub.dto.chat.ChatSeenMessageRequestDTO;
import com.threektechone.resorthub.dto.chat.ChatSendMessageRequestDTO;
import com.threektechone.resorthub.dto.chat.ChatTypingRequestDTO;
import com.threektechone.resorthub.dto.chat.ChatTypingEventDTO;
import com.threektechone.resorthub.dto.chat.ConversationResponseDTO;
import com.threektechone.resorthub.enums.ChatMessageStatus;
import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.models.ChatMessage;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.repositories.ChatMessageRepository;
import com.threektechone.resorthub.repositories.UserRepository;
import com.threektechone.resorthub.service.chat.ChatService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private static final String CHAT_MESSAGES_DESTINATION = "/queue/chat.messages";
    private static final String CHAT_TYPING_DESTINATION = "/queue/chat.typing";

    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;

    private User getUserOrThrowByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("Invalid JWT user"));
    }

    private int getUserIdOrThrowByEmail(String email) {
        return getUserOrThrowByEmail(email).getUserId();
    }

    private static void assertChatRoleAllowed(User user) {
        if (user == null || user.getRole() == null || user.getRole().getRoleName() == null) {
            throw new UnauthorizedException("User role is missing");
        }

        RoleName role = user.getRole().getRoleName();
        if (role == RoleName.ADMIN) {
            throw new UnauthorizedException("Admin is not allowed to chat");
        }

        if (role != RoleName.CUSTOMER && role != RoleName.OWNER && role != RoleName.STAFF) {
            throw new UnauthorizedException("User role is not allowed to chat");
        }
    }

    private ChatMessageResponseDTO toMessageDtoForViewer(ChatMessage message, int viewerId) {
        int senderId = message.getSender().getUserId();
        int receiverId = message.getReceiver().getUserId();

        Boolean isRead = message.getIsRead();
        boolean read = Boolean.TRUE.equals(isRead);

        ChatMessageStatus status;
        if (read) {
            status = ChatMessageStatus.READ;
        } else if (receiverId == viewerId) {
            status = ChatMessageStatus.DELIVERED;
        } else {
            status = ChatMessageStatus.SENT;
        }

        return new ChatMessageResponseDTO(
                message.getMessageId(),
                senderId,
                receiverId,
                message.getMessageContent(),
                message.getSendAt(),
                status);
    }

    private ChatMessageResponseDTO toMessageDtoWithExplicitStatus(ChatMessage message, ChatMessageStatus status) {
        return new ChatMessageResponseDTO(
                message.getMessageId(),
                message.getSender().getUserId(),
                message.getReceiver().getUserId(),
                message.getMessageContent(),
                message.getSendAt(),
                status);
    }

    @Override
    @Transactional
    public ChatMessageResponseDTO sendMessage(String senderEmail, ChatSendMessageRequestDTO request) {
        Objects.requireNonNull(senderEmail, "senderEmail must not be null");
        Objects.requireNonNull(request, "request must not be null");
        if (request.getReceiverId() <= 0) {
            throw new IllegalArgumentException("receiverId is invalid");
        }
        if (request.getContent() == null || request.getContent().isBlank()) {
            throw new IllegalArgumentException("content must not be blank");
        }

        User sender = getUserOrThrowByEmail(senderEmail);
        assertChatRoleAllowed(sender);
        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));
        assertChatRoleAllowed(receiver);

        ChatMessage saved = chatMessageRepository.save(ChatMessage.builder()
                .sender(sender)
                .receiver(receiver)
                .messageContent(request.getContent())
                .isRead(false)
                .build());

        ChatMessageResponseDTO senderDto = toMessageDtoWithExplicitStatus(saved, ChatMessageStatus.SENT);
        ChatMessageResponseDTO receiverDto = toMessageDtoWithExplicitStatus(saved, ChatMessageStatus.DELIVERED);

        messagingTemplate.convertAndSendToUser(sender.getEmail(), CHAT_MESSAGES_DESTINATION, senderDto);
        messagingTemplate.convertAndSendToUser(receiver.getEmail(), CHAT_MESSAGES_DESTINATION, receiverDto);

        return senderDto;
    }

    @Override
    @Transactional
    public void markMessageAsRead(String receiverEmail, ChatSeenMessageRequestDTO request) {
        Objects.requireNonNull(receiverEmail, "receiverEmail must not be null");
        Objects.requireNonNull(request, "request must not be null");

        int receiverId = getUserIdOrThrowByEmail(receiverEmail);
        User receiverUser = getUserOrThrowByEmail(receiverEmail);
        assertChatRoleAllowed(receiverUser);

        ChatMessage message = chatMessageRepository.findByMessageId(request.getMessageId())
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));

        if (message.getReceiver().getUserId() != receiverId) {
            throw new UnauthorizedException("Cannot mark message as read");
        }

        message.setIsRead(true);
        ChatMessage saved = chatMessageRepository.save(message);

        ChatMessageResponseDTO receiverDto = toMessageDtoWithExplicitStatus(saved, ChatMessageStatus.READ);
        ChatMessageResponseDTO senderDto = toMessageDtoWithExplicitStatus(saved, ChatMessageStatus.READ);

        messagingTemplate.convertAndSendToUser(receiverEmail, CHAT_MESSAGES_DESTINATION, receiverDto);
        messagingTemplate.convertAndSendToUser(saved.getSender().getEmail(), CHAT_MESSAGES_DESTINATION, senderDto);

    }

    @Override
    public void sendTypingIndicator(String senderEmail, ChatTypingRequestDTO request) {
        Objects.requireNonNull(senderEmail, "senderEmail must not be null");
        Objects.requireNonNull(request, "request must not be null");
        if (request.getReceiverId() <= 0) {
            throw new IllegalArgumentException("receiverId is invalid");
        }

        User sender = getUserOrThrowByEmail(senderEmail);
        assertChatRoleAllowed(sender);
        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));
        assertChatRoleAllowed(receiver);

        ChatTypingEventDTO event = new ChatTypingEventDTO(
                sender.getUserId(),
                receiver.getUserId(),
                request.isTyping());

        messagingTemplate.convertAndSendToUser(receiver.getEmail(), CHAT_TYPING_DESTINATION, event);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessageResponseDTO> getChatHistory(String userEmail, int otherUserId) {
        Objects.requireNonNull(userEmail, "userEmail must not be null");
        if (otherUserId <= 0) {
            throw new IllegalArgumentException("otherUserId is invalid");
        }

        int viewerId = getUserIdOrThrowByEmail(userEmail);

        return chatMessageRepository.findChatHistoryBetweenUsers(viewerId, otherUserId)
                .stream()
                .map(m -> toMessageDtoForViewer(m, viewerId))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConversationResponseDTO> listConversations(String userEmail) {
        Objects.requireNonNull(userEmail, "userEmail must not be null");

        int viewerId = getUserIdOrThrowByEmail(userEmail);
        List<Integer> partners = chatMessageRepository.findConversationPartnerIds(viewerId);

        return partners.stream()
                .map(partnerId -> {
                    var lastMessages = chatMessageRepository.findLastMessageBetweenUsers(
                            viewerId, partnerId, PageRequest.of(0, 1));
                    ChatMessage last = lastMessages.isEmpty() ? null : lastMessages.get(0);
                    long unread = chatMessageRepository.countUnreadMessages(viewerId, partnerId);

                    if (last == null) {
                        return new ConversationResponseDTO(partnerId, null, unread, null);
                    }

                    ChatMessageResponseDTO lastDto = toMessageDtoForViewer(last, viewerId);
                    LocalDateTime lastTs = last.getSendAt();
                    return new ConversationResponseDTO(partnerId, lastDto, unread, lastTs);
                })
                .sorted(Comparator.comparing(ConversationResponseDTO::getLastMessageTimestamp,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
    }
}

