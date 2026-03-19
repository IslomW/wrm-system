package com.sharom.wrm.modules.order.model.dto;

import com.sharom.wrm.modules.order.model.entity.ShipmentStatus;

public record ShipmentResponseDTO(String id,
                                  String shipmentNumber,
                                  ShipmentStatus status) {
}
