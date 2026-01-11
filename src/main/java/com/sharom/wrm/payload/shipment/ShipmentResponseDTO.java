package com.sharom.wrm.payload.shipment;

import com.sharom.wrm.entity.ShipmentStatus;
import com.sharom.wrm.payload.box.BoxDTO;

import java.util.List;

public record ShipmentResponseDTO(String shipmentNumber,
                                  ShipmentStatus status,
                                  List<BoxDTO> boxes) {
}
