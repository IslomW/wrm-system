package com.sharom.wrm.payload;

import com.sharom.wrm.entity.UserType;

public record RegisterRequest(String username,
                              String password,
                              String phoneNumber,
                              String email) {
}
