package com.sharom.wrm.service;

import com.sharom.wrm.entity.Box;

import java.util.List;

public interface BoxService {

    Box create(Box box);

    Box getById(Long id);

    List<Box> getAll();

    Box update(Long id, Box box);

    void delete(Long id);

}
