package com.threektechone.resorthub.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ChatMessages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private int messageId;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(name = "message_content", nullable = false, length = 255)
    private String messageContent;

    @Column(name = "send_at", nullable = false)
    private LocalDateTime sendAt;

    @Column(name = "is_read", nullable = true)
    private boolean isRead = false;

    public ChatMessage() {
    }

    public ChatMessage(User sender, User receiver, String messageContent, LocalDateTime sendAt, boolean isRead) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageContent = messageContent;
        this.sendAt = LocalDateTime.now();
        this.isRead = isRead;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public LocalDateTime getSendAt() {
        return sendAt;
    }

    public void setSendAt(LocalDateTime sendAt) {
        this.sendAt = sendAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "messageId=" + messageId +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", messageContent='" + messageContent + '\'' +
                ", sendAt=" + sendAt +
                ", isRead=" + isRead +
                '}';
    }
}
