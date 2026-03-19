package com.sharom.wrm.modules.user.mapper;

import com.sharom.wrm.modules.user.model.entity.User;
import com.sharom.wrm.modules.user.model.dto.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);

    User toEntity(UserDTO userDto);

    List<UserDTO> toDtoList(List<User> users);
}
