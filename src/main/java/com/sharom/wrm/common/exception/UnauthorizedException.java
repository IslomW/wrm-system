package com.sharom.wrm.common.exception;

import com.sharom.wrm.common.constant.MessageKey;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiException {

    private static final String LOG_MESSAGE = "Unauthorized";

    @Override
    public String getLogMessage() {
        return LOG_MESSAGE;
    }

    public UnauthorizedException(String message, String code) {
        super(message, code, HttpStatus.UNAUTHORIZED);
    }

    public static UnauthorizedException invalidCredentials(){
        return new UnauthorizedException(MessageKey.PASSWORD_INVALID, MessageKey.PASSWORD_INVALID);
    }
}
