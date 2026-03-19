package com.sharom.wrm.modules.user.model.dto;

import com.sharom.wrm.modules.user.model.entity.UserType;

public record UserDTO(String name, UserType userType, String locationId, String clientCode, int telegramId, String email, String phone

) {
}
