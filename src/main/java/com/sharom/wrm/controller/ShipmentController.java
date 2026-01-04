package com.sharom.wrm.controller;


import com.sharom.wrm.entity.Shipment;
import com.sharom.wrm.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    // 1. Create shipment
    @PostMapping
    public ResponseEntity<Shipment> createShipment() {
        Shipment shipment = shipmentService.createShipment();
        return ResponseEntity.status(HttpStatus.CREATED).body(shipment);
    }

    // 2. Add box to shipment
    @PostMapping("/{shipmentId}/boxes/{boxId}")
    public ResponseEntity<Void> addBoxToShipment(
            @PathVariable String shipmentId,
            @PathVariable String boxId
    ) {
        shipmentService.addBoxToShipment(shipmentId, boxId);
        return ResponseEntity.ok().build();
    }

    // 3. Remove box from shipment
    @DeleteMapping("/{shipmentId}/boxes/{boxId}")
    public ResponseEntity<Void> removeBoxFromShipment(
            @PathVariable String shipmentId,
            @PathVariable String boxId
    ) {
        shipmentService.removeBoxFromShipment(shipmentId, boxId);
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
    @GetMapping("/{shipmentId}")
    public ResponseEntity<Shipment> getShipmentById(
            @PathVariable String shipmentId
    ) {
        Shipment shipment = shipmentService.getById(shipmentId);
        return ResponseEntity.ok(shipment);
    }
}
