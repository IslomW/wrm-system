package com.sharom.wrm.utils;

import org.springframework.data.domain.Page;

public class Page2DTO {

    public static <T> PageDTO<T> tPageDTO(Page<T> page){
        return PageDTO.<T>builder()
                .next(page.hasNext())
                .previous(page.hasPrevious())
                .items(page.getContent())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .pageNumber(page.getNumber())
                .totalPages(page.getTotalPages())
                .build();
    }

}
