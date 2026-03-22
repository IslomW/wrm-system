package com.sharom.wrm.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseFactory {


    public static <T>ResponseEntity<ApiResponse<T>> ok(T data){
        return ResponseEntity.ok(build("Success", data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(build("Created", data));
    }

    private static <T> ApiResponse<T> build(String message, T data) {
        return ApiResponse.<T>builder()
                .message(message)
                .data(data)
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
