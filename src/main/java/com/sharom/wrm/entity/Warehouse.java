package com.sharom.wrm.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table( name = "warehouses",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "code")
        }
        )
public class Warehouse extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String code;
    // "TSH-WH-01" — ЖИВЁТ ВЕЧНО

    @Column(nullable = false)
    private String name; // "Tashkent Hub A"

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private boolean active;

}
