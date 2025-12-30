package com.sharom.wrm.repo;

import com.sharom.wrm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, String> {
}
