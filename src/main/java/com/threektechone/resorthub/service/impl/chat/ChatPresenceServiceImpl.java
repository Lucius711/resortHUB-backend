package com.threektechone.resorthub.service.impl.chat;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Service;

import com.threektechone.resorthub.service.chat.ChatPresenceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatPresenceServiceImpl implements ChatPresenceService {

    // userId -> online status
    private final ConcurrentMap<Integer, Boolean> onlineByUserId = new ConcurrentHashMap<>();

    @Override
    public void markOnline(int userId) {
        onlineByUserId.put(userId, true);
    }

    @Override
    public void markOffline(int userId) {
        onlineByUserId.put(userId, false);
    }

    @Override
    public boolean isOnline(int userId) {
        return onlineByUserId.getOrDefault(userId, false);
    }
}

