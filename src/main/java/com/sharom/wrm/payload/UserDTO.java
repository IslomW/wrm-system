package com.sharom.wrm.payload;

import com.sharom.wrm.entity.UserType;

public record UserDTO(String name, UserType userType, String locationId, String clientCode, int telegramId, String email, String phone

) {
}
