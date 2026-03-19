package com.sharom.wrm.modules.inventory.mapper;

import com.sharom.wrm.modules.inventory.model.entity.BoxGroup;
import com.sharom.wrm.modules.inventory.model.dto.BoxGroupDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoxGroupMapper {
    BoxGroupDTO toDto(BoxGroup boxGroup);
}
