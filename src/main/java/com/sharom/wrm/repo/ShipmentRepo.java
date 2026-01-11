package com.sharom.wrm.repo;

import com.sharom.wrm.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShipmentRepo extends JpaRepository<Shipment, String> {

    Optional<Shipment> findByShipmentNumber(String shipmentNumber);
}
