package com.sharom.wrm.repo;

import com.sharom.wrm.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepo extends JpaRepository<Shipment, String> {
}
