package com.sharom.wrm.controller;

import com.sharom.wrm.entity.Warehouse;
import com.sharom.wrm.payload.WarehouseDTO;
import com.sharom.wrm.service.WarehouseService;
import com.sharom.wrm.utils.PageDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/warehouses")
@AllArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;


    @PostMapping
    public ResponseEntity<WarehouseDTO> create(
            @RequestBody WarehouseDTO warehouseDTO) {

        WarehouseDTO created = warehouseService.create(warehouseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @PutMapping("/{id}")
    public ResponseEntity<WarehouseDTO> update(
            @PathVariable String id,
            @RequestBody WarehouseDTO warehouseDTO) {

        WarehouseDTO updated = warehouseService.update(id, warehouseDTO);
        return ResponseEntity.ok(updated);
    }


    @PostMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable String id) {

        warehouseService.deactivate(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable String id) {

        warehouseService.activate(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<WarehouseDTO> getById(@PathVariable String id) {

        return ResponseEntity.ok(warehouseService.getById(id));
    }


    @GetMapping("/code/{code}")
    public ResponseEntity<WarehouseDTO> getByCode(@PathVariable String code) {

        return ResponseEntity.ok(warehouseService.getByCode(code));
    }

    @GetMapping("/active")
    public ResponseEntity<PageDTO<WarehouseDTO>> getActiveWarehouses(
            Pageable pageable) {

        return ResponseEntity.ok(
                warehouseService.getActiveWarehouses(pageable)
        );
    }

}
