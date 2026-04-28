package com.sharom.wrm.modules.user.model.dto;

public record UserRegisteredEvent(String email,
                                  String name) {
}