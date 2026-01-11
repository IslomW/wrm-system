package com.sharom.wrm.service;

import com.sharom.wrm.payload.shipment.ShipmentPlanedDTO;
import com.sharom.wrm.payload.shipment.ShipmentResponseDTO;

import java.util.List;

public interface ShipmentService {

    ShipmentResponseDTO createShipment(List<String> boxGroupIds);

    void addBoxToShipment(
            String shipmentNumber,
            String boxId
    );

    void lockShipment(String shipmentNumber);

    void addGroupBoxesToShipment(String shipmentNumber, String groupId);

    void removeGroupBoxesFromShipment(String shipmentNumber, String groupId);

    void removeBoxFromShipment(
            String shipmentId,
            String boxId
    );

    void dispatchShipment(String shipmentId);

    void arriveShipment(String shipmentId);

    ShipmentResponseDTO getByNumber(String shipmentNumber);

    ShipmentPlanedDTO getShipmentPlaned(String shipmentNumber);

}
