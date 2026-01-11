package com.sharom.wrm.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class BoxEvent extends BaseEntity{

    @ManyToOne
    private Box box;

    @Enumerated(EnumType.STRING)
    private BoxEventType type;

    @Enumerated(EnumType.STRING)
    private LocationType locationType;

    private String locationId; // warehouseId / truckId / customsId

    @ManyToOne
    private Shipment shipment;

    private LocalDateTime eventTime;

    private String comment;
}
