package com.sharom.wrm.payload;

import java.util.List;

public record BoxHistoryResponse(BoxDTO box, List<StatusHistoryDTO> statuses,List<LocationHistoryDto> locations ) {
}
