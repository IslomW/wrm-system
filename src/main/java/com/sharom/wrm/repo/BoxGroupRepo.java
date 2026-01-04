package com.sharom.wrm.repo;

import com.sharom.wrm.entity.BoxGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoxGroupRepo extends JpaRepository<BoxGroup, String> {

    int countByOrderId(String orderId);
}
