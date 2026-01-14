package com.sharom.wrm.service.impl;

import com.sharom.wrm.entity.User;
import com.sharom.wrm.entity.UserType;
import com.sharom.wrm.entity.Warehouse;
import com.sharom.wrm.exception.BadRequestAlertException;
import com.sharom.wrm.mapper.UserMapper;
import com.sharom.wrm.payload.AuthResponse;
import com.sharom.wrm.payload.RegisterRequest;
import com.sharom.wrm.payload.UserDTO;
import com.sharom.wrm.repo.UserRepo;
import com.sharom.wrm.repo.WarehouseRepo;
import com.sharom.wrm.service.RefreshTokenService;
import com.sharom.wrm.service.UserService;
import com.sharom.wrm.utils.JwtUtil;
import com.sharom.wrm.utils.Page2DTO;
import com.sharom.wrm.utils.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final WarehouseRepo warehouseRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final UserMapper userMapper;


    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepo.existsUserByUserName(request.username())){
            throw BadRequestAlertException.userAlreadyExists();
        }

        User user = new User();
        user.setUserName(request.username());
        user.setEmail(request.email());
        user.setPhone(request.phoneNumber());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setUserType(UserType.CLIENT);
        user.setLocationId("none");

        userRepo.save(user);

        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = refreshTokenService.createRefreshToken(user.getUserName());

        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public AuthResponse refreshAccessToken(String refreshToken) {
        String username = refreshTokenService.validateRefreshToken(refreshToken);
        User user = userRepo.findById(username)
                .orElseThrow(BadRequestAlertException::userNotFound);

        String newAccessToken = jwtUtil.generateToken(user);
        String newRefreshToken = refreshTokenService.createRefreshToken(user.getUserName());

        return new AuthResponse(newAccessToken, newRefreshToken);
    }

    @Override
    public AuthResponse login(String username, String password) {
        User user = userRepo.findByUserName(username)
                .orElseThrow(BadRequestAlertException::userNotFound);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = refreshTokenService.createRefreshToken(user.getUserName());

        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public UserDTO create(UserDTO client) {
        return userMapper.toDto(userRepo.save(userMapper.toEntity(client)));
    }

    @Override
    public UserDTO getById(String id) {
        return userMapper.toDto(userRepo.findById(id)
                .orElseThrow(BadRequestAlertException::userNotFound));
    }

    @Override
    public List<UserDTO> getAll() {
        return userMapper.toDtoList(userRepo.findAll());
    }

    @Override
    public PageDTO<UserDTO> getAll(Pageable pageable) {
        Page<UserDTO> users = userRepo.findAll(pageable).map(userMapper::toDto);
        return Page2DTO.tPageDTO(users);
    }

    @Override
    public UserDTO update(String id, UserDTO client) {
        User existingUser = userMapper.toEntity(getById(id));

        existingUser.setUserName(client.name());
        existingUser.setClientCode(client.clientCode());
        existingUser.setTelegramId(client.telegramId());
        existingUser.setClientCode(client.email());
        existingUser.setPhone(client.phone());

        return userMapper.toDto(userRepo.save(existingUser));
    }

    @Override
    public void delete(String id) {
        userRepo.deleteById(id);
    }


    @Override
    public UserDTO setUserLocation(String userId, String locationId) {
        User user = userRepo.findById(userId)
                .orElseThrow(BadRequestAlertException::userNotFound);
        Warehouse warehouse = warehouseRepo.findById(locationId)
                .orElseThrow();

        user.setLocationId(warehouse.getId());
        userRepo.save(user);
        return userMapper.toDto(user);
    }
}
