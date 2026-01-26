package com.sharom.wrm.mapper;

import com.sharom.wrm.entity.User;
import com.sharom.wrm.payload.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);

    User toEntity(UserDTO userDto);

    List<UserDTO> toDtoList(List<User> users);
}
