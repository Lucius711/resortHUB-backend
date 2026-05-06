package com.threektechone.resorthub.service.impl.security;

import java.util.Collections;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import com.threektechone.resorthub.service.security.RateLimitService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RateLimitServiceImpl implements RateLimitService {

    private final StringRedisTemplate redisTemplate;

    private final DefaultRedisScript<Long> rateLimitScript = createRateLimitScript();

    @Override
public boolean allow(String key, int limit, int windowSeconds) {
    String redisKey = "rl:" + key;

    long start = System.currentTimeMillis();

    try {
        Long result = redisTemplate.execute(
                rateLimitScript,
                Collections.singletonList(redisKey),
                String.valueOf(limit),
                String.valueOf(windowSeconds)
        );

        long duration = System.currentTimeMillis() - start;

        log.info("""
                [RATE_LIMIT]
                key={}
                limit={}
                window={}s
                result={}
                allowed={}
                duration={}ms
                """,
                redisKey,
                limit,
                windowSeconds,
                result,
                result != null && result == 1L,
                duration
        );

        return result != null && result == 1L;

    } catch (Exception e) {
        log.error("[RATE_LIMIT_ERROR] key={} error={}", redisKey, e.getMessage(), e);

        return true; // fallback safe
    }
}

    private static DefaultRedisScript<Long> createRateLimitScript() {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();

        script.setScriptText("""
                local current = redis.call("INCR", KEYS[1])

                if current == 1 then
                  redis.call("EXPIRE", KEYS[1], tonumber(ARGV[2]))
                end

                if current > tonumber(ARGV[1]) then
                  return 0
                end

                return 1
                """);

        script.setResultType(Long.class);
        return script;
    }
}
