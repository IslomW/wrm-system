package com.sharom.wrm.payload;

import com.sharom.wrm.payload.box.BoxDTO;

import java.util.List;

public record BoxHistoryResponse(BoxDTO box, List<StatusHistoryDTO> statuses, List<LocationHistoryDTO> locations ) {
}
