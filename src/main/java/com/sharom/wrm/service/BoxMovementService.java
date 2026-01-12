package com.sharom.wrm.service;

import com.sharom.wrm.entity.BoxEvent;
import com.sharom.wrm.payload.box.BoxEventDTO;
import com.sharom.wrm.utils.PageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BoxMovementService {


    void loadToTruck(String boxId, String shipmentNumber);

    void unload(String boxId, String shipmentNumber);

    void reloadToAnotherTruck(String boxId, String shipmentNumber);

    void arrivedAtCustoms(String boxId, String shipmentNumber);

    PageDTO<BoxEvent> getHistory(String  boxId, Pageable pageable);

    BoxEvent getLastEvent(String boxId);

    PageDTO<BoxEventDTO> getHistoryByShipment(String shipmentNumber, Pageable pageable);

    PageDTO<BoxEventDTO> getLastEventByShipment(String shipmentNumber, Pageable pageable);
}

