package com.sharom.wrm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class BoxLocationHistory extends BaseEntity{

    @ManyToOne(optional = false)
    private Box box;

    @ManyToOne(optional = false)
    private Warehouse warehouse;

    private LocalDateTime arrivedAt;

}
