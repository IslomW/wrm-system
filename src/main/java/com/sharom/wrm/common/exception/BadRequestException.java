package com.sharom.wrm.common.exception;

import org.springframework.http.HttpStatus;

import static com.sharom.wrm.common.constant.MessageKey.*;

public class BadRequestException extends ApiException {

    private static final String LOG_MESSAGE = "Bad Request";

    @Override
    public String getLogMessage() {
        return LOG_MESSAGE;
    }

    public BadRequestException(String message, String code) {
        super(message, code, HttpStatus.BAD_REQUEST);
    }


    public static BadRequestException invalidVerificationCode() {
        return new BadRequestException(VERIFICATION_CODE_INVALID, VERIFICATION_CODE_INVALID);
    }

    public static BadRequestException resetPasswordNotAllowed() {
        return new BadRequestException(RESET_PASSWORD_NOT_ALLOWED, RESET_PASSWORD_NOT_ALLOWED);
    }

    public static BadRequestException expiredCode() {
        return new BadRequestException(EXPIRED_CODE, EXPIRED_CODE);
    }

    public static BadRequestException errorInvalidFileFormat() {
        return new BadRequestException(
                ERROR_INVALID_FILE_FORMAT,
                ERROR_INVALID_FILE_FORMAT
        );
    }
}
