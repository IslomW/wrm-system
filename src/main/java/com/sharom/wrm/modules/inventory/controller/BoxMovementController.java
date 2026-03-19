package com.sharom.wrm.modules.inventory.controller;

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
    public PageDTO<BoxEvent> getHistory(
            @PathVariable String boxId,
            Pageable pageable
    ) {
        return boxMovementService.getHistory(boxId, pageable);
    }

    @GetMapping("/{boxId}/last-event")
    public BoxEvent getLastEvent(
            @PathVariable String boxId
    ) {
        return boxMovementService.getLastEvent(boxId);
    }

    @GetMapping("/shipment/{shipmentNumber}/history")
    public PageDTO<BoxEventDTO> getHistoryByShipment(
            @PathVariable String shipmentNumber,
            Pageable pageable
    ) {
        return boxMovementService.getHistoryByShipment(shipmentNumber, pageable);
    }

    @GetMapping("/shipment/{shipmentId}/last-event")
    public PageDTO<BoxEventDTO> getLastEventByShipment(
            @PathVariable String shipmentNumber,
            Pageable pageable
    ) {
        return boxMovementService.getLastEventByShipment(shipmentNumber, pageable);
    }

    @GetMapping("/{shipmentNumber}")
    public List<BoxGroupResponseDTO> getAllBox(@PathVariable String shipmentNumber){
        return boxMovementService.getAllBoxByShipment(shipmentNumber);
    }
}
