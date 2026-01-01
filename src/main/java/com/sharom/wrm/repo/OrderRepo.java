package com.sharom.wrm.repo;

import com.sharom.wrm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, String> {
    List<Order> findByClientId(String clientId);
}
