package com.sharom.wrm.service;

import com.sharom.wrm.payload.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO create(UserDTO client);

    UserDTO getById(String id);

    List<UserDTO> getAll();

    UserDTO update(String id, UserDTO client);

    void delete(String id);

}
