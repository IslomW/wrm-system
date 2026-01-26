package com.sharom.wrm.payload.box;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BoxGroupDTO(String id,
                          String description,
                          String boxType,
                          int quantity,
                          BigDecimal weight,
                          BigDecimal length,
                          BigDecimal width,
                          BigDecimal height,
                          List<String> photoUrls) {
}
