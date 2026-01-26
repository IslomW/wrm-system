package com.sharom.wrm.mapper;

import com.sharom.wrm.entity.BoxGroup;
import com.sharom.wrm.payload.box.BoxGroupDTO;
import com.sharom.wrm.payload.box.BoxGroupResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoxGroupMapper {

    BoxGroupDTO boxGroupToDTO(BoxGroup boxGroup);

    BoxGroupResponseDTO toResponseDTO(BoxGroup boxGroup);
}
