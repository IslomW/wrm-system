package com.sharom.wrm.modules.inventory.model.dto;

import java.util.List;

public record BoxGroupResponseDTO(String id,
                                  String description,
                                  int quantity,
                                  List<BoxDTO> box,
                                  List<String> photos) {
}
