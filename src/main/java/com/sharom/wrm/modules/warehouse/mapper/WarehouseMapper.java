package com.sharom.wrm.modules.warehouse.mapper;

import com.sharom.wrm.modules.warehouse.model.entity.Warehouse;
import com.sharom.wrm.modules.warehouse.model.dto.WarehouseDTO;

public interface WarehouseMapper {

    Warehouse toEntity(WarehouseDTO dto);

    WarehouseDTO toDto(Warehouse entity);

    void updateEntity(Warehouse warehouse, WarehouseDTO dto);

}
