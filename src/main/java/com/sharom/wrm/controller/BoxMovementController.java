package com.sharom.wrm.controller;

import com.sharom.wrm.entity.BoxEvent;
import com.sharom.wrm.payload.box.BoxEventDTO;
import com.sharom.wrm.service.BoxMovementService;
import com.sharom.wrm.utils.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/box-movements")
@RequiredArgsConstructor
public class BoxMovementController {


    private final BoxMovementService boxMovementService;

    @PostMapping("/load")
    public ResponseEntity<Void> loadToTruck(
            @RequestParam String boxId,
            @RequestParam String shipmentNumber
    ) {
        boxMovementService.loadToTruck(boxId, shipmentNumber);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unload")
    public ResponseEntity<Void> unload(
            @RequestParam String boxId,
            @RequestParam String shipmentNumber
    ) {
        boxMovementService.unload(boxId, shipmentNumber);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reload")
    public ResponseEntity<Void> reloadToAnotherTruck(
            @RequestParam String boxId,
            @RequestParam String shipmentNumber
    ) {
        boxMovementService.reloadToAnotherTruck(boxId, shipmentNumber);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/customs")
    public ResponseEntity<Void> arrivedAtCustoms(
            @RequestParam String boxId,
            @RequestParam String shipmentNumber
    ) {
        boxMovementService.arrivedAtCustoms(boxId, shipmentNumber);
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
}
