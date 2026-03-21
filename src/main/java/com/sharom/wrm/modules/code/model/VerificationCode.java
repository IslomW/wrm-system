package com.sharom.wrm.modules.code.model;

import com.sharom.wrm.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "verification_code")
@ToString(callSuper = true)
public class VerificationCode extends BaseEntity {
    private String code;
    private String  userId;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
}
