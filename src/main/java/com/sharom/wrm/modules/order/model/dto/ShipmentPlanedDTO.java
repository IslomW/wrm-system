package com.sharom.wrm.modules.order.model.dto;

import com.sharom.wrm.modules.order.model.entity.ShipmentStatus;
import com.sharom.wrm.modules.inventory.model.dto.BoxGroupResponseDTO;

import java.util.List;

public record ShipmentPlanedDTO(String shipmentNumber, ShipmentStatus status, List<BoxGroupResponseDTO> boxes) {
}
