package com.sharom.wrm.modules.warehouse.service;

import com.sharom.wrm.modules.warehouse.model.dto.WarehouseDTO;
import com.sharom.wrm.common.util.PageDTO;
import org.springframework.data.domain.Pageable;

public interface WarehouseService {

    WarehouseDTO create(WarehouseDTO warehouseDTO);

    WarehouseDTO update(String warehouseId, WarehouseDTO warehouseDTO);

    void deactivate(String warehouseId);

    void activate(String warehouseId);

    WarehouseDTO getById(String warehouseId);

    WarehouseDTO getByCode(String code);

    PageDTO<WarehouseDTO> getActiveWarehouses(Pageable pageable);

    void validateIsActive(String warehouseId);
}
