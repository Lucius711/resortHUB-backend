package com.threektechone.resorthub.service.chat;

public interface ChatPresenceService {
    void markOnline(int userId);

    void markOffline(int userId);

    boolean isOnline(int userId);
}

