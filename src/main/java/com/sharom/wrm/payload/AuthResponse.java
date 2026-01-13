package com.sharom.wrm.payload;

public record AuthResponse(String accessToken,
                           String refreshToken) {
}
