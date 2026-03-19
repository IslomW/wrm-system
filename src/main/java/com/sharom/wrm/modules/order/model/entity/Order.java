package com.sharom.wrm.modules.order.model.entity;

import com.sharom.wrm.common.entity.AuditEntity;
import com.sharom.wrm.modules.inventory.model.entity.BoxGroup;
import com.sharom.wrm.modules.user.model.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order extends AuditEntity {

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<BoxGroup> boxGroups;
}

