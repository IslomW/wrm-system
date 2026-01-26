package com.sharom.wrm.payload;

import com.sharom.wrm.entity.UserType;

public record RegisterRequest(
        String username,
        String name,
        String email,
        String phoneNumber,
        String password
) {
}
