package com.sharom.wrm.service.impl;

import com.sharom.wrm.entity.Order;
import com.sharom.wrm.entity.OrderStatus;
import com.sharom.wrm.entity.User;
import com.sharom.wrm.repo.OrderRepo;
import com.sharom.wrm.repo.UserRepo;
import com.sharom.wrm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final UserRepo userRepo;

    @Override
    public Order createOrder(String clientId) {
        User client = userRepo.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Order order = new Order();
        order.setClient(client);
        order.setStatus(OrderStatus.CREATED);

        return orderRepo.save(order);
    }

    @Override
    public Order getById(String id) {
        return orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Order> getAll() {
        return orderRepo.findAll();
    }

    @Override
    public void changeStatus(String orderId, OrderStatus newStatus) {
        Order order = getById(orderId);

        OrderStatusTransitions.validate(order.getStatus(), newStatus);

        order.setStatus(newStatus);
    }

    @Override
    public void delete(String id) {
        Order order = getById(id);

        if (order.getStatus() == OrderStatus.SHIPPED ||
                order.getStatus() == OrderStatus.COMPLETED) {
            throw new IllegalStateException("Cannot delete shipped or delivered order");
        }

        orderRepo.delete(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getByClientId(String clientId) {
        return orderRepo.findByClientId(clientId);
    }
}
