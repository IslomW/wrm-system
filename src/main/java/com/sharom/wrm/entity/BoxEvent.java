package com.sharom.wrm.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "box_event",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_box_event_once",
                        columnNames = {"box_id", "shipment_number", "type"}
                )
        }
)
@Getter
@Setter
public class BoxEvent extends BaseEntity{

    @ManyToOne
    private Box box;

    @Enumerated(EnumType.STRING)
    private BoxEventType type;

    private String truckNumber;

    @Enumerated(EnumType.STRING)
    private LocationType locationType;

    private String locationId; // warehouseId / truckId / customsId

    private String operatorId;

    @Column(nullable = false)
    private String shipmentNumber;

    private LocalDateTime eventTime;

    private String fromLocation;

    private String toLocation;

    private String comment;
}
