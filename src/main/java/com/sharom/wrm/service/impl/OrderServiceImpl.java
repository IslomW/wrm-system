package com.sharom.wrm.service.impl;

import com.sharom.wrm.entity.Order;
import com.sharom.wrm.entity.OrderStatus;
import com.sharom.wrm.service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    @Override
    public Order createOrder(String clientId) {
        return null;
    }

    @Override
    public Order getById(Long id) {
        return null;
    }

    @Override
    public List<Order> getAll() {
        return List.of();
    }

    @Override
    public void changeStatus(String orderId, OrderStatus newStatus) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Order> getByClientId(Long clientId) {
        return List.of();
    }
}
