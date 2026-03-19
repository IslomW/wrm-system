package com.sharom.wrm.modules.inventory.mapper;

import com.sharom.wrm.modules.inventory.model.entity.Box;
import com.sharom.wrm.modules.inventory.model.dto.BoxDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoxMapper {

    BoxDTO toDto(Box box);

    List<BoxDTO> toDtoList(List<Box> boxes);


}
