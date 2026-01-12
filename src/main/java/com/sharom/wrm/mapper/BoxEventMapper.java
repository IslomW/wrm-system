package com.sharom.wrm.mapper;

import com.sharom.wrm.entity.BoxEvent;
import com.sharom.wrm.payload.box.BoxEventDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoxEventMapper {
    BoxEvent toEntity(BoxEventDTO dto);
    BoxEventDTO toDto(BoxEvent entity);
}
