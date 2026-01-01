package com.sharom.wrm.payload;

import java.time.LocalDateTime;

public record LocationHistoryDTO(String warehouseName,
                                 String hubName,
                                 String transportInfo,
                                 LocalDateTime arrivedAt) {
}
