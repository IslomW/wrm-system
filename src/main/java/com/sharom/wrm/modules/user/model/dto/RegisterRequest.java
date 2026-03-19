package com.sharom.wrm.modules.user.model.dto;

public record RegisterRequest(String username,
                              String password,
                              String phoneNumber,
                              String email) {
}
