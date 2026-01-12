package com.sharom.wrm.service;

import com.sharom.wrm.entity.BoxEvent;
import com.sharom.wrm.utils.PageDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface BoxMovementService {


    void loadToTruck(String boxId, String shipmentNumber);

    void unload(String boxId, String shipmentNumber);

    void reloadToAnotherTruck(String boxId, String shipmentNumber);

    void arrivedAtCustoms(String boxId, String shipmentNumber);

    PageDTO<BoxEvent> getHistory(String  boxId, int page, int size);

    BoxEvent getLastEvent(String boxId);

    PageDTO<BoxEvent> getHistoryByShipment(String shipmentId, int page, int size);

    PageDTO<BoxEvent> getLastEventByShipment(String shipmentId, int page, int size);
}

