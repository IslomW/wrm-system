package com.sharom.wrm.service.impl;

import com.sharom.wrm.entity.Warehouse;
import com.sharom.wrm.mapper.WarehouseMapper;
import com.sharom.wrm.payload.WarehouseDTO;
import com.sharom.wrm.repo.WarehouseRepo;
import com.sharom.wrm.service.WarehouseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {


    private final WarehouseRepo warehouseRepo;

    private final WarehouseMapper mapper;


    @Override
    public WarehouseDTO create(WarehouseDTO warehouseDTO) {

        Warehouse warehouse = mapper.toEntity(warehouseDTO);
        Warehouse saved = warehouseRepo.save(warehouse);

        return mapper.toDto(warehouseRepo.save(saved));
    }

    @Override
    public WarehouseDTO update(String warehouseId, WarehouseDTO warehouseDTO) {

        Warehouse warehouse = warehouseRepo.findById(warehouseId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Warehouse not found: id=" + warehouseId));

        validateIsActive(warehouseId);

        mapper.updateEntity(warehouse, warehouseDTO);

        Warehouse saved = warehouseRepo.save(warehouse);
        return mapper.toDto(saved);
    }

    @Override
    public void deactivate(String warehouseId) {

        Warehouse warehouse = warehouseRepo.findById(warehouseId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Warehouse not found: id=" + warehouseId));

        if (!warehouse.isActive()) {
            return;
        }

        warehouse.setActive(false);
        warehouseRepo.save(warehouse);

    }

    @Override
    public void activate(String warehouseId) {

        Warehouse warehouse = warehouseRepo.findById(warehouseId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Warehouse not found: id=" + warehouseId));

        if (warehouse.isActive()) {
            return;
        }

        warehouse.setActive(true);
        warehouseRepo.save(warehouse);

    }

    @Override
    public WarehouseDTO getById(String warehouseId) {
        Warehouse warehouse = warehouseRepo.findById(warehouseId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Warehouse not found: id=" + warehouseId));

        return mapper.toDto(warehouse);
    }

    @Override
    public WarehouseDTO getByCode(String code) {
        Warehouse warehouse = warehouseRepo.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Warehouse not found: code=" + code));

        return mapper.toDto(warehouse);
    }

    @Override
    public Page<Warehouse> getActiveWarehouses(Pageable pageable) {
        return warehouseRepo.findAllByActiveTrue(pageable);
    }

    @Override
    public void validateIsActive(String warehouseId) {


        Warehouse warehouse = warehouseRepo.findById(warehouseId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Warehouse not found: id=" + warehouseId));

        if (!warehouse.isActive()) {
            throw new IllegalStateException(
                    "Warehouse is inactive: id=" + warehouseId);
        }

    }
}
