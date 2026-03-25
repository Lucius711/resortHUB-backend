package com.threektechone.resorthub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import com.threektechone.resorthub.models.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    
    Optional<ChatMessage> findByMessageId(int messageId);

    @Query("""
        select m from ChatMessage m
        where (m.sender.userId = :userId and m.receiver.userId = :otherUserId)
           or (m.sender.userId = :otherUserId and m.receiver.userId = :userId)
        order by m.sendAt asc
    """)
    List<ChatMessage> findChatHistoryBetweenUsers(
            @Param("userId") int userId,
            @Param("otherUserId") int otherUserId);

    @Query("""
        select distinct case
            when m.sender.userId = :userId then m.receiver.userId
            else m.sender.userId
        end
        from ChatMessage m
        where m.sender.userId = :userId or m.receiver.userId = :userId
    """)
    List<Integer> findConversationPartnerIds(@Param("userId") int userId);

    @Query("""
        select m from ChatMessage m
        where (m.sender.userId = :userId and m.receiver.userId = :partnerId)
           or (m.sender.userId = :partnerId and m.receiver.userId = :userId)
        order by m.sendAt desc
    """)
    List<ChatMessage> findLastMessageBetweenUsers(
            @Param("userId") int userId,
            @Param("partnerId") int partnerId,
            Pageable pageable);

    @Query("""
        select count(m) from ChatMessage m
        where m.sender.userId = :partnerId
          and m.receiver.userId = :userId
          and (m.isRead = false or m.isRead is null)
    """)
    long countUnreadMessages(
            @Param("userId") int userId,
            @Param("partnerId") int partnerId);
}
