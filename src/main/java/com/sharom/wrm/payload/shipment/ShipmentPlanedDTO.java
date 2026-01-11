package com.sharom.wrm.payload.shipment;

import com.sharom.wrm.entity.ShipmentStatus;
import com.sharom.wrm.payload.box.BoxGroupResponseDTO;

import java.util.List;

public record ShipmentPlanedDTO(String shipmentNumber, ShipmentStatus status, List<BoxGroupResponseDTO> boxes) {
}
