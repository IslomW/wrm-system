package com.sharom.wrm.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Code {

    SUCCESS,
    DATA_NOT_ACTIVE,
    ACCESS_DENIED,
    DATA_NOT_FOUND,
    ALREADY_EXISTS,
    INVALID_DATA,
    UNEXPECTED_ERROR,
    UNAVAILABLE,
    FORBIDDEN,
    OPERATION_EXPIRED,
    OPERATION_FAILED
}
