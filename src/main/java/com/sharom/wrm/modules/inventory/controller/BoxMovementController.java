package com.sharom.wrm.modules.inventory.controller;

import com.sharom.wrm.common.response.ApiResponse;
import com.sharom.wrm.common.response.ResponseFactory;
import com.sharom.wrm.modules.inventory.model.entity.BoxAction;
import com.sharom.wrm.modules.inventory.model.entity.BoxEvent;
import com.sharom.wrm.modules.inventory.model.dto.BoxEventDTO;
import com.sharom.wrm.modules.inventory.model.dto.BoxGroupResponseDTO;
import com.sharom.wrm.modules.inventory.service.BoxMovementService;
import com.sharom.wrm.common.util.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/box-movements")
@RequiredArgsConstructor
public class BoxMovementController {


    private final BoxMovementService boxMovementService;

    @PostMapping("/move")
    public ResponseEntity<Void> loadToTruck(
            @RequestParam String boxId,
            @RequestParam String shipmentNumber,
            @RequestParam BoxAction boxAction
    ) {
        boxMovementService.moveBox(boxId, shipmentNumber, boxAction);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{boxId}/history")
    public ResponseEntity<ApiResponse<PageDTO<BoxEvent>>> getHistory(
            @PathVariable String boxId,
            Pageable pageable
    ) {
        return ResponseFactory.ok(boxMovementService.getHistory(boxId, pageable));
    }

    @GetMapping("/{boxId}/last-event")
    public ResponseEntity<ApiResponse<BoxEvent>> getLastEvent(
            @PathVariable String boxId
    ) {
        return ResponseFactory.ok(boxMovementService.getLastEvent(boxId));
    }

    @GetMapping("/shipment/{shipmentNumber}/history")
    public ResponseEntity<ApiResponse<PageDTO<BoxEventDTO>>> getHistoryByShipment(
            @PathVariable String shipmentNumber,
            Pageable pageable
    ) {
        return ResponseFactory.ok(boxMovementService.getHistoryByShipment(shipmentNumber, pageable));
    }

    @GetMapping("/shipment/{shipmentId}/last-event")
    public ResponseEntity<ApiResponse<PageDTO<BoxEventDTO>>> getLastEventByShipment(
            @PathVariable String shipmentNumber,
            Pageable pageable
    ) {
        return ResponseFactory.ok(boxMovementService.getLastEventByShipment(shipmentNumber, pageable));
    }

    @GetMapping("/{shipmentNumber}")
    public ResponseEntity<ApiResponse<List<BoxGroupResponseDTO>>> getAllBox(@PathVariable String shipmentNumber) {
        return ResponseFactory.ok(boxMovementService.getAllBoxByShipment(shipmentNumber));
    }
}
