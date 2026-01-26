package com.sharom.wrm.entity;

import com.sharom.wrm.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "box_group")
@Getter
@Setter
public class BoxGroup extends AuditEntity {

    private String  boxGroupCode;
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

    @OneToMany(
            mappedBy = "boxGroup",
            cascade = CascadeType.ALL
    )
    private List<Box> boxes = new ArrayList<>();

    // тип товара внутри (опционально)
    private String boxType;

    private int quantity;

    private BigDecimal extraExpenses = BigDecimal.ZERO;

    @ElementCollection
    @CollectionTable(
            name = "box_group_photos",
            joinColumns = @JoinColumn(name = "box_group_id")
    )
    @Column(name = "photo_url")
    private List<String> photoUrls = new ArrayList<>();

    public void removeBox(Box box) {
        boxes.remove(box);      // обновляем коллекцию
        box.setBoxGroup(null);  // обновляем owning side
    }
}
