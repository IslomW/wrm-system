package com.sharom.wrm.service;

import com.sharom.wrm.entity.Order;

import java.util.List;

public interface OrderService {
    Order create(Order order);

    Order getById(Long id);

    List<Order> getAll();

    Order update(Long id, Order order);

    void delete(Long id);

    List<Order> getByClientId(Long clientId);

}
