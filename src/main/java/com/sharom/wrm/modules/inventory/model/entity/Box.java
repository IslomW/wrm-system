package com.sharom.wrm.modules.inventory.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sharom.wrm.common.entity.AuditEntity;
import com.sharom.wrm.modules.warehouse.model.entity.Warehouse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Box extends AuditEntity {

    private String boxNumber;        // BOX-001
    private String description;


    @Column(name = "qr_code", nullable = false, unique = true)
    private String qrCode;


    @Enumerated(EnumType.STRING)
    private BoxStatus status;        // CREATED, SEALED

    // группа, к которой относится коробка
    @ManyToOne(optional = false)
    @JoinColumn(name = "box_group_id")
    @JsonIgnore
    private BoxGroup boxGroup;

    @ManyToOne
    private Warehouse currentWarehouse;


}
