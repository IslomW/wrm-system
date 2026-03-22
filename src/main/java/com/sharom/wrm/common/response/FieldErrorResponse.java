package com.sharom.wrm.common.response;

import lombok.Builder;

@Builder
public record FieldErrorResponse(
        String field,
        String code,
        String message
) {
}
