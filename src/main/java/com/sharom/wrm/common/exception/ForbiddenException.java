package com.sharom.wrm.common.exception;

import org.springframework.http.HttpStatus;

import static com.sharom.wrm.common.constant.MessageKey.USER_DISABLED;
import static com.sharom.wrm.common.constant.MessageKey.USER_NOT_ACTIVE;

public class ForbiddenException extends ApiException {

    public ForbiddenException(String message, String code) {
        super(message, code, HttpStatus.FORBIDDEN);
    }

    public static ForbiddenException userDisabled() {
        return new ForbiddenException(USER_DISABLED, USER_DISABLED);
    }

    public static ForbiddenException userNotActive() {
        return new ForbiddenException(USER_NOT_ACTIVE, USER_NOT_ACTIVE);
    }
}
