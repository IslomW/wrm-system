package com.sharom.wrm.mapper;

import com.sharom.wrm.entity.BoxGroup;
import com.sharom.wrm.payload.box.BoxGroupDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoxGroupMapper {
    BoxGroupDTO toDto(BoxGroup boxGroup);
}
