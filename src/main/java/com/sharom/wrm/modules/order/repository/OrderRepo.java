package com.sharom.wrm.modules.order.repository;

import com.sharom.wrm.modules.order.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, String> {
    List<Order> findAllByClientId(String clientId);
}
