package com.sharom.wrm.common.exception;

import org.springframework.http.HttpStatus;

import static com.sharom.wrm.common.constant.MessageKey.ERROR_SEND_CODE_TO_EMAIL;

public class InternalServerException extends ApiException {
    private static final String LOG_MESSAGE = "Internal Server Error";

    @Override
    public String getLogMessage() {
        return LOG_MESSAGE;
    }

    public InternalServerException(String message, String code) {
        super(message, code, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static InternalServerException errorSentCode(){
        return new InternalServerException(ERROR_SEND_CODE_TO_EMAIL, ERROR_SEND_CODE_TO_EMAIL);
    }
}
