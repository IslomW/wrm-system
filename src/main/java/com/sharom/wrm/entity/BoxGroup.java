package com.sharom.wrm.entity;

import com.sharom.wrm.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "box_group")
@Getter
@Setter
public class BoxGroup extends AuditEntity {


    // описание группы (например: "Коробки 60x40x40, бытовая техника")
    @Column(length = 255)
    private String description;

    // размеры (одинаковые для всей группы)
    private BigDecimal weight;
    private BigDecimal length;
    private BigDecimal width;
    private BigDecimal height;

    // к какому заказу относится группа
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // тип товара внутри (опционально)
    private String boxType;

    private int quantity;

    private String photoUrl;
}
