package com.sharom.wrm.controller;


import com.sharom.wrm.payload.shipment.ShipmentPlanedDTO;
import com.sharom.wrm.payload.shipment.ShipmentResponseDTO;
import com.sharom.wrm.service.ShipmentService;
import com.sharom.wrm.utils.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

    @PostMapping("/groups")
    public ResponseEntity<Void> addGroupBoxToShipment(
            @RequestParam String shipmentNumber,
            @RequestParam String groupBoxId
    ) {
        shipmentService.addGroupBoxToShipment(shipmentNumber, groupBoxId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/group")
    public ResponseEntity<Void> addGroupBoxesToShipment(
            @RequestParam String shipmentNumber,
            @RequestParam String groupId
    ) {
        shipmentService.addGroupBoxesToShipment(shipmentNumber, groupId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{shipmentNumber}/ready")
    public ResponseEntity<Void> readyShipment(
            @PathVariable String shipmentNumber
    ) {
        shipmentService.lockShipment(shipmentNumber);
        return ResponseEntity.ok().build();
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


    @GetMapping("/{shipmentNumber}")
    public ResponseEntity<ShipmentResponseDTO> getShipmentById(
            @PathVariable String shipmentNumber
    ) {
        return ResponseEntity.ok(shipmentService.getByNumber(shipmentNumber));
    }

    @GetMapping
    public ResponseEntity<PageDTO<ShipmentResponseDTO>> getAllShipments(
            Pageable pageable
    ){
        return ResponseEntity.ok(shipmentService.getAllShipments(pageable));
    }

    @GetMapping("/planned/{shipmentNumber}")
    public ResponseEntity<ShipmentPlanedDTO> getShipmentPlaned(
            @PathVariable String shipmentNumber
    ) {
        return ResponseEntity.ok(shipmentService.getShipmentPlaned(shipmentNumber));
    }

    @DeleteMapping("group")
    public ResponseEntity<Void> removeGroupBoxesFromShipment(
            @RequestParam String shipmentNumber,
            @RequestParam String groupId) {

        shipmentService.removeGroupBoxesFromShipment(shipmentNumber, groupId);
        return ResponseEntity.noContent().build();
    }


}
