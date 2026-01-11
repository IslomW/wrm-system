package com.sharom.wrm.service;

import com.sharom.wrm.entity.Box;
import com.sharom.wrm.entity.BoxStatus;

import java.util.List;

public interface BoxService {

//    Box create(Box box);
//
//    Box getById(Long id);
//
//    List<Box> getAll();
//
//    Box update(Long id, Box box);
//
//    void delete(Long id);
//
//    void changeStatus(Long boxId, BoxStatus newStatus, User user);


    Box getById(String boxId);


}
