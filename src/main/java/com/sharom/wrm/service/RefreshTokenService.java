package com.sharom.wrm.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RefreshTokenService {

    private final Map<String, String> refreshTokenStorage = new ConcurrentHashMap<>();
    private final long refreshTokenDurationMs = 24 * 60 * 60 * 1000; // 24 часа

    public String createRefreshToken(String username) {
        String token = UUID.randomUUID().toString();
        refreshTokenStorage.put(token, username);
        // Можно добавить expire time, cleanup task
        return token;
    }

    public String validateRefreshToken(String token) {
        String username = refreshTokenStorage.get(token);
        if (username == null) {
            throw new RuntimeException("Invalid refresh token");
        }
        return username;
    }
}
