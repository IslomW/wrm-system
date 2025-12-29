package com.sharom.wrm.service;

import com.sharom.wrm.entity.User;

import java.util.List;

public interface UserService {
    User create(User user);

    User getById(Long id);

    User getByUsername(String username);

    List<User> getAll();

    User update(Long id, User user);

    void delete(Long id);
}
