package com.sharom.wrm.service.impl;

import com.sharom.wrm.entity.User;
import com.sharom.wrm.mapper.UserMapper;
import com.sharom.wrm.payload.UserDTO;
import com.sharom.wrm.repo.UserRepo;
import com.sharom.wrm.service.UserService;
import com.sharom.wrm.utils.Page2DTO;
import com.sharom.wrm.utils.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;


    @Override
    public UserDTO create(UserDTO client) {

        return userMapper.toDto(userRepo.save(userMapper.toEntity(client)));
    }

    @Override
    public UserDTO getById(String id) {
        return userMapper.toDto(userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found")));
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

        existingUser.setName(client.name());
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
}
