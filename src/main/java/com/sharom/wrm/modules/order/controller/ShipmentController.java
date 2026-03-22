package com.sharom.wrm.modules.order.controller;


import com.sharom.wrm.common.response.ApiResponse;
import com.sharom.wrm.common.response.ResponseFactory;
import com.sharom.wrm.modules.order.model.dto.ShipmentPlanedDTO;
import com.sharom.wrm.modules.order.model.dto.ShipmentResponseDTO;
import com.sharom.wrm.modules.order.service.ShipmentService;
import com.sharom.wrm.common.util.PageDTO;
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
    public ResponseEntity<ApiResponse<ShipmentResponseDTO>> createShipment(
            @RequestParam List<String> boxGroupIds
    ) {
        return ResponseFactory.created(shipmentService.createShipment(boxGroupIds));
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
    public ResponseEntity<ApiResponse<ShipmentResponseDTO>> getShipmentById(
            @PathVariable String shipmentNumber
    ) {
        return ResponseFactory.ok(shipmentService.getByNumber(shipmentNumber));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageDTO<ShipmentResponseDTO>>> getAllShipments(
            Pageable pageable
    ){
        return ResponseFactory.ok(shipmentService.getAllShipments(pageable));
    }

    @GetMapping("/planned/{shipmentNumber}")
    public ResponseEntity<ApiResponse<ShipmentPlanedDTO>> getShipmentPlaned(
            @PathVariable String shipmentNumber
    ) {
        return ResponseFactory.ok(shipmentService.getShipmentPlaned(shipmentNumber));
    }

    @DeleteMapping("group")
    public ResponseEntity<Void> removeGroupBoxesFromShipment(
            @RequestParam String shipmentNumber,
            @RequestParam String groupId) {

        shipmentService.removeGroupBoxesFromShipment(shipmentNumber, groupId);
        return ResponseEntity.noContent().build();
    }


}
