package com.sharom.wrm.payload;

import java.util.List;

public record BoxGroupResponseDTO(String id,
                                  String description,
                                  int quantity,
                                  List<BoxDTO> box) {
}
