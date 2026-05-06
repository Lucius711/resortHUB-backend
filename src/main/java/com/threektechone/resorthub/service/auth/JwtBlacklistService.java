package com.threektechone.resorthub.service.auth;

import java.time.Instant;

public interface JwtBlacklistService {
    void blacklistJti(String jti, Instant expiresAt);

    boolean isBlacklisted(String jti);
}
