package com.sharom.wrm.modules.user.service;

import com.sharom.wrm.modules.user.model.dto.AuthResponse;
import com.sharom.wrm.modules.user.model.dto.RegisterRequest;
import com.sharom.wrm.modules.user.model.dto.ForgotPasswordRequest;
import com.sharom.wrm.modules.user.model.dto.VerifyForgotPasswordRequest;
import com.sharom.wrm.modules.user.model.dto.ResetPasswordRequest;
import com.sharom.wrm.modules.user.model.dto.UserDTO;
import com.sharom.wrm.common.util.PageDTO;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface UserService {

    UserDTO getCurrentUser();

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

    UserDTO setUserLocation(String userId, String locationId);

    void forgotPassword(ForgotPasswordRequest request);

    void verifyForgotPassword(VerifyForgotPasswordRequest req);

    void resetPassword(ResetPasswordRequest req);
}
