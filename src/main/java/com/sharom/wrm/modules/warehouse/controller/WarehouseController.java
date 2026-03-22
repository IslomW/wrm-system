package com.sharom.wrm.modules.warehouse.controller;

import com.sharom.wrm.common.response.ApiResponse;
import com.sharom.wrm.common.response.ResponseFactory;
import com.sharom.wrm.modules.warehouse.model.dto.WarehouseDTO;
import com.sharom.wrm.modules.warehouse.service.WarehouseService;
import com.sharom.wrm.common.util.PageDTO;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<ApiResponse<WarehouseDTO>> create(
            @RequestBody WarehouseDTO warehouseDTO) {

        WarehouseDTO created = warehouseService.create(warehouseDTO);
        return ResponseFactory.created(created);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<WarehouseDTO>> update(
            @PathVariable String id,
            @RequestBody WarehouseDTO warehouseDTO) {

        WarehouseDTO updated = warehouseService.update(id, warehouseDTO);
        return ResponseFactory.ok(updated);
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
    public ResponseEntity<ApiResponse<WarehouseDTO>> getById(@PathVariable String id) {

        return ResponseFactory.ok(warehouseService.getById(id));
    }


    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse<WarehouseDTO>> getByCode(@PathVariable String code) {

        return ResponseFactory.ok(warehouseService.getByCode(code));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<PageDTO<WarehouseDTO>>> getActiveWarehouses(
            Pageable pageable) {

        return ResponseFactory.ok(
                warehouseService.getActiveWarehouses(pageable)
        );
    }

}
