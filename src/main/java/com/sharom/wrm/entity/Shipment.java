package com.sharom.wrm.entity;

import jakarta.persistence.Enumerated;

public class Shipment {


    private String shipmentNumber;   // SHP-2025-001
    private String clientName;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;
}
