package com.sharom.wrm.payload;

import com.sharom.wrm.entity.BoxStatus;

public record BoxDTO(String id, String qrCode,
                     BoxStatus status, String currentLocation) {
}
