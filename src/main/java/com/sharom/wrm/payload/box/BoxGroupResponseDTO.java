package com.sharom.wrm.payload.box;

import java.util.List;

public record BoxGroupResponseDTO(String id,
                                  String description,
                                  String boxGroupCode,
                                  int quantity,
                                  List<BoxDTO> box,
                                  List<String> photos) {
}
