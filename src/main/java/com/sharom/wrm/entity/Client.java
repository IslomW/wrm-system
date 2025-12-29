package com.sharom.wrm.entity;

import com.sharom.wrm.audit.AuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client extends AuditEntity {


    @Column(nullable = false)
    private String name;

    private String clientCode;

    private int telegramId;
    private String email;
    private String phone;
}
