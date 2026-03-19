package com.sharom.wrm.modules.inventory.repository;

import com.sharom.wrm.modules.inventory.model.entity.BoxGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoxGroupRepo extends JpaRepository<BoxGroup, String> {

    int countByOrderId(String orderId);
}
