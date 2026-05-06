package com.threektechone.resorthub.service.security;

public interface RateLimitService {
    boolean allow(String key, int limit, int windowSeconds);
}
