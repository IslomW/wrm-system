package com.sharom.wrm.controller;


import com.sharom.wrm.payload.shipment.ShipmentPlanedDTO;
import com.sharom.wrm.payload.shipment.ShipmentResponseDTO;
import com.sharom.wrm.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    // 1. Create shipment
    @PostMapping
    public ResponseEntity<ShipmentResponseDTO> createShipment(
            @RequestParam List<String> boxGroupIds
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shipmentService.createShipment(boxGroupIds));
    }

    // 2. Add box to shipment
    @PostMapping("box")
    public ResponseEntity<Void> addBoxToShipment(
            @RequestParam String shipmentNumber,
            @RequestParam String boxId
    ) {
        shipmentService.addBoxToShipment(shipmentNumber, boxId);
        return ResponseEntity.ok().build();
    }

    // 3. Remove box from shipment
    @DeleteMapping("box")
    public ResponseEntity<Void> removeBoxFromShipment(
            @RequestParam String shipmentId,
            @RequestParam String boxId
    ) {
        shipmentService.removeBoxFromShipment(shipmentId, boxId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("group")
    public ResponseEntity<Void> removeGroupBoxesFromShipment(
            @RequestParam String shipmentNumber,
            @RequestParam String groupId) {

        shipmentService.removeGroupBoxesFromShipment(shipmentNumber, groupId);
        return ResponseEntity.noContent().build();
    }

    // 4. Dispatch shipment
    @PostMapping("/{shipmentId}/dispatch")
    public ResponseEntity<Void> dispatchShipment(
            @PathVariable String shipmentId
    ) {
        shipmentService.dispatchShipment(shipmentId);
        return ResponseEntity.ok().build();
    }

    // 5. Arrive shipment
    @PostMapping("/{shipmentId}/arrive")
    public ResponseEntity<Void> arriveShipment(
            @PathVariable String shipmentId
    ) {
        shipmentService.arriveShipment(shipmentId);
        return ResponseEntity.ok().build();
    }

    // 6. Get shipment by id
    @GetMapping("/{shipmentNumber}")
    public ResponseEntity<ShipmentResponseDTO> getShipmentById(
            @PathVariable String shipmentNumber
    ) {
        return ResponseEntity.ok(shipmentService.getByNumber(shipmentNumber));
    }

    @GetMapping("/planned/{shipmentNumber}")
    public ResponseEntity<ShipmentPlanedDTO> getShipmentPlaned(
            @PathVariable String shipmentNumber
    ) {
        return ResponseEntity.ok(shipmentService.getShipmentPlaned(shipmentNumber));
    }
}
