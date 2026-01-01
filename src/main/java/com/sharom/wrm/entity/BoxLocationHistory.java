package com.sharom.wrm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class BoxLocationHistory extends BaseEntity{

    @Column(nullable = true)
    private String transportInfo;

    @ManyToOne(optional = false)
    private Box box;

    @ManyToOne(optional = false)
    private Warehouse warehouse;

    private LocalDateTime arrivedAt;

}
