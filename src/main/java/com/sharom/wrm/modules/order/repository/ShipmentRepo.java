package com.sharom.wrm.modules.order.repository;

import com.sharom.wrm.modules.order.model.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShipmentRepo extends JpaRepository<Shipment, String> {

    Optional<Shipment> findByShipmentNumber(String shipmentNumber);
}
