package com.sharom.wrm.entity;

import com.sharom.wrm.audit.AuditEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BoxStatusHistory extends AuditEntity {

    @ManyToOne(optional = false)
    private Box box;

    @Enumerated(EnumType.STRING)
    private BoxStatus boxStatus;



}
