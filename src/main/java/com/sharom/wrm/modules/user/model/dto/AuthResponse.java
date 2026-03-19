package com.sharom.wrm.modules.user.model.dto;

public record AuthResponse(String accessToken,
                           String refreshToken) {
}
