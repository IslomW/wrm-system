package com.sharom.wrm.service;

import com.sharom.wrm.entity.Order;
import com.sharom.wrm.entity.OrderStatus;

import java.util.List;

public interface OrderService {
    Order createOrder(String clientId);

    Order getById(String  id);

    List<Order> getAll();

    void changeStatus(
            String orderId,
            OrderStatus newStatus
    );

    void delete(String  id);

    List<Order> getByClientId(String  clientId);

}
