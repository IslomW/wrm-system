package com.sharom.wrm.entity;

import jakarta.persistence.*;

import static jakarta.persistence.FetchType.*;

public class Box {

    private String boxNumber;        // BOX-001
    private String description;

    @Column(name = "qr_code_value", nullable = false, unique = true)
    private String qrCodeValue;

    @Enumerated(EnumType.STRING)
    private BoxStatus status;        // CREATED, SEALED, SCANNED


    @ManyToOne(fetch = LAZY)
    private Shipment shipment;
}
