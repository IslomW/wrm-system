package com.sharom.wrm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Shipment extends BaseEntity {


    @Column(nullable = false, unique = true)
    private String shipmentNumber;// SHP-2025-001

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    // ПЛАН — группы, которые разрешено грузить
    @ManyToMany
    @JoinTable(
            name = "shipment_planned_groups",
            joinColumns = @JoinColumn(name = "shipment_id"),
            inverseJoinColumns = @JoinColumn(name = "box_group_id")
    )
    private List<BoxGroup> plannedGroups = new ArrayList<>();
}
