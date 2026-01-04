package com.sharom.wrm.repo;

import com.sharom.wrm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, String> {
    List<Order> findAllByClientId(String clientId);
}
