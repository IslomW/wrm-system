package com.sharom.wrm.service;

import com.sharom.wrm.entity.Warehouse;
import com.sharom.wrm.payload.WarehouseDTO;
import com.sharom.wrm.utils.PageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WarehouseService {

    WarehouseDTO create(WarehouseDTO warehouseDTO);

    WarehouseDTO update(String warehouseId, WarehouseDTO warehouseDTO);

    void deactivate(String warehouseId);

    void activate(String warehouseId);

    WarehouseDTO getById(String warehouseId);

    WarehouseDTO getByCode(String code);

    PageDTO<Warehouse> getActiveWarehouses(Pageable pageable);

    void validateIsActive(String warehouseId);
}
