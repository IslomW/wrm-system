package com.sharom.wrm.mapper;

import com.sharom.wrm.entity.Shipment;
import com.sharom.wrm.payload.shipment.ShipmentResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShipmentMapper {

    ShipmentResponseDTO toShipmentResponseDTO(Shipment shipment);
}
