package com.sharom.wrm.service.impl;

import com.sharom.wrm.entity.Box;
import com.sharom.wrm.entity.BoxLocationHistory;
import com.sharom.wrm.entity.Warehouse;
import com.sharom.wrm.repo.BoxLocationHistoryRepo;
import com.sharom.wrm.repo.BoxRepo;
import com.sharom.wrm.repo.WarehouseRepo;
import com.sharom.wrm.service.BoxLocationService;
import com.sharom.wrm.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class BoxLocationServiceImpl implements BoxLocationService {

    private final BoxRepo boxRepository;
    private final WarehouseRepo warehouseRepository;
    private final BoxLocationHistoryRepo historyRepository;
    private final WarehouseService warehouseService;

    @Override
    public void arriveToWarehouse(String boxId, String warehouseId) {
        Box box = boxRepository.findById(boxId)
                .orElseThrow(() -> new RuntimeException("Box not found"));

        warehouseService.validateIsActive(warehouseId);

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        BoxLocationHistory history = new BoxLocationHistory();
        history.setBox(box);
        history.setWarehouse(warehouse);
        history.setArrivedAt(LocalDateTime.now());

        historyRepository.save(history);
    }

    @Override
    public void moveBetweenWarehouses(String boxId, String fromWarehouseId, String toWarehouseId) {

        Warehouse current = getCurrentWarehouse(boxId);

        if (!current.getId().equals(fromWarehouseId)) {
            throw new IllegalStateException("Box is not in expected warehouse");
        }

        arriveToWarehouse(boxId, toWarehouseId);
    }

    @Override
    @Transactional(readOnly = true)
    public Warehouse getCurrentWarehouse(String boxId) {
        return historyRepository
                .findTopByBoxIdOrderByArrivedAtDesc(boxId)
                .map(BoxLocationHistory::getWarehouse)
                .orElseThrow(() -> new RuntimeException("Box has no location history"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoxLocationHistory> getHistory(String boxId) {
        return historyRepository.findByBoxIdOrderByArrivedAtDesc(boxId);
    }
}
