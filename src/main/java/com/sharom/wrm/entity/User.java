package com.sharom.wrm.entity;

import com.sharom.wrm.audit.AuditEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends AuditEntity {

    private String  userName;
    private String clientCode;

    private String password;
    
    @Enumerated(EnumType.STRING)
    private UserType userType;

    private String locationId;

    private int telegramId;
    private String email;
    private String phone;
}
