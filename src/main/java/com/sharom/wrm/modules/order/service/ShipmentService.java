package com.sharom.wrm.modules.order.service;

import com.sharom.wrm.modules.order.model.dto.ShipmentPlanedDTO;
import com.sharom.wrm.modules.order.model.dto.ShipmentResponseDTO;
import com.sharom.wrm.common.util.PageDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShipmentService {

    ShipmentResponseDTO createShipment(List<String> boxGroupIds);

    void addBoxToShipment(
            String shipmentNumber,
            String boxId
    );

    void addGroupBoxToShipment(String shipmentNumber, String groupBoxId);

    void lockShipment(String shipmentNumber);

    void addGroupBoxesToShipment(String shipmentNumber, String groupId);

    void removeGroupBoxesFromShipment(String shipmentNumber, String groupId);

    ShipmentResponseDTO getByNumber(String shipmentNumber);

    PageDTO<ShipmentResponseDTO> getAllShipments(Pageable pageable);

    ShipmentPlanedDTO getShipmentPlaned(String shipmentNumber);

}
