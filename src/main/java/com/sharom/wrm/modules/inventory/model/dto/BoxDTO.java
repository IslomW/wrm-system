package com.sharom.wrm.modules.inventory.model.dto;

import com.sharom.wrm.modules.inventory.model.entity.BoxStatus;

public record BoxDTO(String id, String qrCode,
                     BoxStatus status, String currentLocation) {
}
