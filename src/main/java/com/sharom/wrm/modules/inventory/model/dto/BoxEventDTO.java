package com.sharom.wrm.modules.inventory.model.dto;

import com.sharom.wrm.modules.inventory.model.entity.BoxEventType;
import com.sharom.wrm.modules.inventory.model.entity.LocationType;

import java.time.LocalDateTime;

public record BoxEventDTO(
        String id,
        BoxDTO box,
        BoxEventType eventType,
        LocationType locationType,
        String location,
        String shipmentNumber,
        LocalDateTime eventTime,
        String comment
) {
}
