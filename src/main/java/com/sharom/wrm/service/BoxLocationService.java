package com.sharom.wrm.service;

import com.sharom.wrm.entity.BoxLocationHistory;
import com.sharom.wrm.entity.Warehouse;

import java.util.List;

public interface BoxLocationService {

    //void arriveToWarehouse(String boxId, String warehouseId) – коробка прибыла на склад
    void arriveToWarehouse(
            String boxId,
            String warehouseId
    );

    void moveToTransit(
            String boxId,
            String hubName,
            String transportInfo
    );

    void arriveToHub(
            String boxId,
            String hubName
    );



    void moveBetweenWarehouses(
            String boxId,
            String fromWarehouseId,
            String toWarehouseId
    );

    Warehouse getCurrentWarehouse(String boxId);

    List<BoxLocationHistory> getHistory(String boxId);
}
