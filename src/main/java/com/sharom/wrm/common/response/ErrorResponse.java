package com.sharom.wrm.common.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ErrorResponse(
        int status,
        String message,
        List<FieldErrorResponse> errors,
        long timestamp,
        String path
) {

}
