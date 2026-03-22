package com.sharom.wrm.modules.user.service;

import com.sharom.wrm.common.constant.MessageKey;
import com.sharom.wrm.common.exception.BadRequestException;
import com.sharom.wrm.common.exception.NotFoundException;
import com.sharom.wrm.common.exception.UnauthorizedException;
import com.sharom.wrm.common.util.Page2DTO;
import com.sharom.wrm.common.util.PageDTO;
import com.sharom.wrm.config.security.CustomUserDetails;
import com.sharom.wrm.config.security.JwtUtil;
import com.sharom.wrm.config.security.SecurityUtils;
import com.sharom.wrm.modules.code.model.VerificationCode;
import com.sharom.wrm.modules.code.repository.CodeRepo;
import com.sharom.wrm.modules.code.service.VerificationCodeApiService;
import com.sharom.wrm.modules.code.service.VerificationCodeService;
import com.sharom.wrm.modules.user.mapper.UserMapper;
import com.sharom.wrm.modules.user.model.dto.*;
import com.sharom.wrm.modules.user.model.entity.User;
import com.sharom.wrm.modules.user.model.entity.UserType;
import com.sharom.wrm.modules.user.repository.UserRepo;
import com.sharom.wrm.modules.warehouse.model.entity.Warehouse;
import com.sharom.wrm.modules.warehouse.repository.WarehouseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final WarehouseRepo warehouseRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final UserMapper userMapper;
    private final VerificationCodeService verificationCodeService;
    private final VerificationCodeApiService verificationCodeApiService;
    private final CodeRepo codeRepo;


    @Override
    public UserDTO getCurrentUser() {
        CustomUserDetails userDetails = SecurityUtils.currentUser();
        User user = userRepo.findById(userDetails.getId())
                .orElseThrow(NotFoundException::userNotFound);
        return userMapper.toDto(user);
    }

    @Override
    public AuthResponse register(RegisterRequest request) {

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
                .orElseThrow(NotFoundException::userNotFound);

        String newAccessToken = jwtUtil.generateToken(user);
        String newRefreshToken = refreshTokenService.createRefreshToken(user.getUserName());

        return new AuthResponse(newAccessToken, newRefreshToken);
    }

    @Override
    public AuthResponse login(String username, String password) {
        User user = userRepo.findByUserName(username)
                .orElseThrow(NotFoundException::userNotFound);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw UnauthorizedException.invalidCredentials();
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
                .orElseThrow(NotFoundException::userNotFound));
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
                .orElseThrow(NotFoundException::userNotFound);
        Warehouse warehouse = warehouseRepo.findById(locationId)
                .orElseThrow();

        user.setLocationId(warehouse.getId());
        userRepo.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public ResponseEntity<?> forgotPassword(ForgotPasswordRequest request) throws ResponseStatusException {
        User user = userRepo.
                findUserByEmail(request.email()).orElseThrow(NotFoundException::userNotFound);

        String code = verificationCodeService.getCode();

        if (!verificationCodeApiService.sendCodeToEmail(request.email(), code)) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    MessageKey.ERROR_SEND_CODE_TO_EMAIL
            );
        }

//        Optional<VerificationCode> existingCode = codeRepo.findByUserId(user.getId());
//
//        if (existingCode.isPresent()) {
//
//            VerificationCode codeEntity = existingCode.get();
//
//            if (codeEntity.getCreatedAt().plusMinutes(1).isAfter(LocalDateTime.now())) {
//                throw BadRequestException.tooManyRequest();
//            }
//
//        }


//        codeRepo.deleteByUserId(user.getId());

        VerificationCode c = VerificationCode.builder()
                .userId(user.getId())
                .code(code)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(2))
                .build();

        codeRepo.save(c);

        return ResponseEntity.ok("RESET CODE SENT SUCCESSFULLY");

    }

    @Override
    public ResponseEntity<?> verifyForgotPassword(VerifyForgotPasswordRequest req) {
        User user = userRepo.
                findUserByEmail(req.email()).orElseThrow(NotFoundException::userNotFound);


        VerificationCode code = codeRepo.findByUserId(user.getId())
                .orElseThrow(BadRequestException::invalidVerificationCode);


        if (!code.getCode().equals(req.code())) {
            throw BadRequestException.invalidVerificationCode();
        }

        if (code.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw BadRequestException.expiredCode();
        }

//        codeRepo.deleteVerificationCodeByUserId(user.getId());

        user.setResetPasswordAllowed(true);
        userRepo.save(user);

        return ResponseEntity.ok("CODE VERIFIED. YOU CAN NOW RESET PASSWORD.");
    }

    @Override
    public ResponseEntity<?> resetPassword(ResetPasswordRequest req) {
        User user = userRepo.findUserByEmail(req.email())
                .orElseThrow(NotFoundException::userNotFound);

        if (!Boolean.TRUE.equals(user.getResetPasswordAllowed())) {
            throw BadRequestException.resetPasswordNotAllowed();
        }

        user.setPassword(passwordEncoder.encode(req.newPassword()));
        user.setResetPasswordAllowed(false);
        userRepo.save(user);

        return ResponseEntity.ok("PASSWORD RESET SUCCESSFULLY");    }
}
