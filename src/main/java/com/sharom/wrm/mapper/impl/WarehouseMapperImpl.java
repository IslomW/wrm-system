package com.sharom.wrm.mapper.impl;

import com.sharom.wrm.entity.Warehouse;
import com.sharom.wrm.mapper.WarehouseMapper;
import com.sharom.wrm.payload.WarehouseDTO;
import org.springframework.stereotype.Component;

@Component
public class WarehouseMapperImpl implements WarehouseMapper {
    @Override
    public Warehouse toEntity(WarehouseDTO dto) {
        if (dto == null) {
            return null;
        }

        Warehouse warehouse = new Warehouse();
        warehouse.setCode(dto.code());
        warehouse.setName(dto.name());
        warehouse.setCountry(dto.country());
        warehouse.setCity(dto.city());
        warehouse.setActive(true); // по умолчанию создаём активный склад

        return warehouse;
    }

    @Override
    public WarehouseDTO toDto(Warehouse entity) {
        if (entity == null) {
            return null;
        }

        return new WarehouseDTO(
                entity.getCode(),
                entity.getName(),
                entity.getCountry(),
                entity.getCity()
        );
    }

    @Override
    public void updateEntity(Warehouse warehouse, WarehouseDTO dto) {
        if (warehouse == null || dto == null) {
            return;
        }

        // 🔒 Запрещённые к изменению поля:
        // id, code, active — НЕ трогаем

        warehouse.setName(dto.name());
        warehouse.setCountry(dto.country());
        warehouse.setCity(dto.city());
    }
}
