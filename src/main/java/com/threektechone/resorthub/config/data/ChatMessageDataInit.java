package com.threektechone.resorthub.config.data;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.javafaker.Faker;
import com.threektechone.resorthub.models.ChatMessage;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.repositories.ChatMessageRepository;
import com.threektechone.resorthub.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@Order(6)
@RequiredArgsConstructor
public class ChatMessageDataInit implements CommandLineRunner {

    private final ChatMessageRepository chatRepository;
    private final UserRepository userRepository;

    private Faker faker;

    @PostConstruct
    public void init() {
        faker = new Faker();
    }
    
    @Transactional
    @Override
    public void run(String... args) {

        // tránh seed lại
        if (chatRepository.count() > 0) return;

        List<User> users = userRepository.findAll();

        if (users.size() < 2) return;

        for (int i = 0; i < 20; i++) {

            User sender = users.get(faker.random().nextInt(users.size()));
            User receiver;

            // ❗ đảm bảo sender != receiver
            do {
                receiver = users.get(faker.random().nextInt(users.size()));
            } while (receiver.getUserId() == sender.getUserId());

            ChatMessage message = ChatMessage.builder()
                    .sender(sender)
                    .receiver(receiver)
                    .messageContent(faker.lorem().sentence())
                    .isRead(faker.bool().bool())
                    .build();

            chatRepository.save(message);
        }

        System.out.println("✅ Seeded 20 chat messages!");
    }
}