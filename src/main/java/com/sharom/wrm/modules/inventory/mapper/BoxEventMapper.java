package com.sharom.wrm.modules.inventory.mapper;

import com.sharom.wrm.modules.inventory.model.entity.BoxEvent;
import com.sharom.wrm.modules.inventory.model.dto.BoxEventDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoxEventMapper {
    BoxEvent toEntity(BoxEventDTO dto);
    BoxEventDTO toDto(BoxEvent entity);
}
