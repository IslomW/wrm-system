package com.sharom.wrm.mapper.impl;

import com.sharom.wrm.entity.User;
import com.sharom.wrm.mapper.UserMapper;
import com.sharom.wrm.payload.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapperImpl implements UserMapper {
    @Override
    public UserDTO toDto(User user) {
        if (user == null) {
            return null;
        }

        return new UserDTO(
                user.getName(),
                user.getClientCode(),
                user.getTelegramId(),
                user.getEmail(),
                user.getPhone()
        );
    }

    @Override
    public User toEntity(UserDTO userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setName(userDto.name());
        user.setClientCode(userDto.clientCode());
        user.setTelegramId(userDto.telegramId());
        user.setEmail(userDto.email());
        user.setPhone(userDto.phone());
        return user;
    }

    @Override
    public List<UserDTO> toDtoList(List<User> users) {

        if (users == null) {
            return List.of();
        }


        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
