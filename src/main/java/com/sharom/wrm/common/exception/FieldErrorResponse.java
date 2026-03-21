package com.sharom.wrm.common.exception;

import lombok.Builder;

@Builder
public record FieldErrorResponse(
        String field,
        String code,
        String message
) {
}
