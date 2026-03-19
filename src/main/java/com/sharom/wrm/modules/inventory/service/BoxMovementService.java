package com.sharom.wrm.modules.inventory.service;

import com.sharom.wrm.modules.inventory.model.entity.BoxAction;
import com.sharom.wrm.modules.inventory.model.entity.BoxEvent;
import com.sharom.wrm.modules.inventory.model.dto.BoxEventDTO;
import com.sharom.wrm.modules.inventory.model.dto.BoxGroupResponseDTO;
import com.sharom.wrm.common.util.PageDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoxMovementService {

    void moveBox(String boxId, String shipmentNumber, BoxAction action);

    PageDTO<BoxEvent> getHistory(String  boxId, Pageable pageable);

    BoxEvent getLastEvent(String boxId);

    PageDTO<BoxEventDTO> getHistoryByShipment(String shipmentNumber, Pageable pageable);

    PageDTO<BoxEventDTO> getLastEventByShipment(String shipmentNumber, Pageable pageable);

    List<BoxGroupResponseDTO> getAllBoxByShipment(String shipmentNumber);
}

