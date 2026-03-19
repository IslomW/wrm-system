package com.sharom.wrm.modules.order.mapper;

import com.sharom.wrm.modules.order.model.entity.Shipment;
import com.sharom.wrm.modules.order.model.dto.ShipmentResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShipmentMapper {

    ShipmentResponseDTO toShipmentResponseDTO(Shipment shipment);
}
