package com.sharom.wrm.service;

import com.sharom.wrm.payload.shipment.ShipmentPlanedDTO;
import com.sharom.wrm.payload.shipment.ShipmentResponseDTO;
import com.sharom.wrm.utils.PageDTO;
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
