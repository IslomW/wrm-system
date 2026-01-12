package com.sharom.wrm.payload.box;

import com.sharom.wrm.entity.BoxEventType;
import com.sharom.wrm.entity.LocationType;

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
