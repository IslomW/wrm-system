package com.sharom.wrm.common.response;

import lombok.Builder;

@Builder
public record ApiResponse<T>(
        String message,
        T data,
        long timestamp
) {
}
