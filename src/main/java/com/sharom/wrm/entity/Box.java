package com.sharom.wrm.entity;

import com.sharom.wrm.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;


@Entity
@Getter
@Setter
public class Box extends AuditEntity {

    private String boxNumber;        // BOX-001
    private String description;


    @Column(name = "qr_code", nullable = false, unique = true)
    private String qrCode;


    @Enumerated(EnumType.STRING)
    private BoxStatus status;        // CREATED, SEALED, SCANNED

    // группа, к которой относится коробка
    @ManyToOne(optional = false)
    private BoxGroup boxGroup;

}
