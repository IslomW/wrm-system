package com.sharom.wrm.service;

import com.sharom.wrm.payload.AuthResponse;
import com.sharom.wrm.payload.RegisterRequest;
import com.sharom.wrm.payload.UserDTO;
import com.sharom.wrm.utils.PageDTO;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface UserService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(String username, String password);

    AuthResponse refreshAccessToken(String refreshToken);


    UserDTO create(UserDTO client);

    UserDTO getById(String id);

    List<UserDTO> getAll();
    // Dashboard
    PageDTO<UserDTO> getAll(Pageable pageable);

    UserDTO update(String id, UserDTO client);

    void delete(String id);

}
