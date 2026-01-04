package com.sharom.wrm.controller;


import com.sharom.wrm.entity.BoxLocationHistory;
import com.sharom.wrm.entity.Warehouse;
import com.sharom.wrm.payload.BoxHistoryResponse;
import com.sharom.wrm.service.BoxHistoryService;
import com.sharom.wrm.service.BoxLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boxes")
@RequiredArgsConstructor
public class BoxLocationController {

    private final BoxLocationService boxLocationService;

    private final BoxHistoryService boxHistoryService;

    // 1. Box arrived to warehouse
    @PostMapping("/{boxId}/arrive/warehouse/{warehouseId}")
    public ResponseEntity<Void> arriveToWarehouse(
            @PathVariable String boxId,
            @PathVariable String warehouseId
    ) {
        boxLocationService.arriveToWarehouse(boxId, warehouseId);
        return ResponseEntity.ok().build();
    }

    // 2. Move box to transit
    @PostMapping("/{boxId}/transit")
    public ResponseEntity<Void> moveToTransit(
            @PathVariable String boxId,
            @RequestParam String hubName,
            @RequestParam String transportInfo
    ) {
        boxLocationService.moveToTransit(boxId, hubName, transportInfo);
        return ResponseEntity.ok().build();
    }

    // 3. Box arrived to hub
    @PostMapping("/{boxId}/arrive/hub")
    public ResponseEntity<Void> arriveToHub(
            @PathVariable String boxId,
            @RequestParam String hubName
    ) {
        boxLocationService.arriveToHub(boxId, hubName);
        return ResponseEntity.ok().build();
    }

    // 4. Move box between warehouses
    @PostMapping("/{boxId}/move")
    public ResponseEntity<Void> moveBetweenWarehouses(
            @PathVariable String boxId,
            @RequestParam String fromWarehouseId,
            @RequestParam String toWarehouseId
    ) {
        boxLocationService.moveBetweenWarehouses(
                boxId,
                fromWarehouseId,
                toWarehouseId
        );
        return ResponseEntity.ok().build();
    }

    // 5. Get current warehouse of box
    @GetMapping("/{boxId}/warehouse")
    public ResponseEntity<Warehouse> getCurrentWarehouse(
            @PathVariable String boxId
    ) {
        Warehouse warehouse = boxLocationService.getCurrentWarehouse(boxId);
        return ResponseEntity.ok(warehouse);
    }

    // 6. Get box location history
    @GetMapping("/{boxId}/history")
    public ResponseEntity<List<BoxLocationHistory>> getHistory(
            @PathVariable String boxId
    ) {
        return ResponseEntity.ok(
                boxLocationService.getHistory(boxId)
        );
    }

    @GetMapping("/box/history/{boxId}")
    public ResponseEntity<BoxHistoryResponse> getBoxHistory(@PathVariable String boxId){
        return ResponseEntity.ok(boxHistoryService.getFullHistory(boxId));

    }
}
