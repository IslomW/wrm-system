package com.sharom.wrm.payload;

import com.sharom.wrm.entity.BoxStatus;

import java.time.LocalDateTime;

public record StatusHistoryDTO(BoxStatus status,
                               LocalDateTime changedAt,
                               String changedBy) {
}
