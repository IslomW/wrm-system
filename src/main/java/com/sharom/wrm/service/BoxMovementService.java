package com.sharom.wrm.service;

import com.sharom.wrm.entity.BoxAction;
import com.sharom.wrm.entity.BoxEvent;
import com.sharom.wrm.payload.box.BoxEventDTO;
import com.sharom.wrm.utils.PageDTO;
import org.springframework.data.domain.Pageable;

public interface BoxMovementService {

    void moveBox(String boxId, String shipmentNumber, BoxAction action);

    PageDTO<BoxEvent> getHistory(String  boxId, Pageable pageable);

    BoxEvent getLastEvent(String boxId);

    PageDTO<BoxEventDTO> getHistoryByShipment(String shipmentNumber, Pageable pageable);

    PageDTO<BoxEventDTO> getLastEventByShipment(String shipmentNumber, Pageable pageable);
}

