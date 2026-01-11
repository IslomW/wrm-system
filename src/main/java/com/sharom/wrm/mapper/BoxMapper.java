package com.sharom.wrm.mapper;

import com.sharom.wrm.entity.Box;
import com.sharom.wrm.payload.box.BoxDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoxMapper {

    BoxDTO toDto(Box box);

    List<BoxDTO> toDtoList(List<Box> boxes);


}
