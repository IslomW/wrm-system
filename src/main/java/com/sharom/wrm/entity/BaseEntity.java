package com.sharom.wrm.entity;

import com.sharom.wrm.utils.TsidGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)

public class BaseEntity {


    @Id
    @Column(length = 27, updatable = false, nullable = false)
    private String id;

    @Version
    private Long version;

    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = TsidGenerator.next();
        }
    }
}
