package com.sharom.wrm.mapper;

import com.sharom.wrm.entity.Warehouse;
import com.sharom.wrm.payload.WarehouseDTO;

public interface WarehouseMapper {

    Warehouse toEntity(WarehouseDTO dto);

    WarehouseDTO toDto(Warehouse entity);

    void updateEntity(Warehouse warehouse, WarehouseDTO dto);

}
