package com.sharom.wrm.service;

import com.sharom.wrm.entity.Shipment;

public interface ShipmentService {

    Shipment createShipment();

    void addBoxToShipment(
            String shipmentId,
            String boxId
    );

    void removeBoxFromShipment(
            String shipmentId,
            String boxId
    );

    void dispatchShipment(String shipmentId);

    void arriveShipment(String shipmentId);

    Shipment getById(String shipmentId);

}
