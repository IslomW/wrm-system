package com.sharom.wrm.common.exception;

import org.springframework.http.HttpStatus;

import static com.sharom.wrm.common.constant.MessageKey.*;

public class BadRequestException extends ApiException{

    public BadRequestException(String message, String code) {
        super(message, code, HttpStatus.BAD_REQUEST);
    }


    public static BadRequestException usernameEmpty() {
        return new BadRequestException(USERNAME_REQUIRED, USERNAME_REQUIRED);
    }

    public static BadRequestException usernameTooShort() {
        return new BadRequestException(USERNAME_TOO_SHORT, USERNAME_TOO_SHORT);
    }

    public static BadRequestException emptyPassword() {
        return new BadRequestException(PASSWORD_REQUIRED, PASSWORD_REQUIRED);
    }

    public static BadRequestException emptyPhone() {
        return new BadRequestException(PHONE_EMPTY, PHONE_EMPTY);
    }

    public static BadRequestException invalidPhone() {
        return new BadRequestException(PHONE_INVALID, PHONE_INVALID);
    }

    public static BadRequestException emptyEmail() {
        return new BadRequestException(EMAIL_REQUIRED, EMAIL_REQUIRED);
    }

    public static BadRequestException invalidEmail() {
        return new BadRequestException(EMAIL_INVALID, EMAIL_INVALID);
    }


}
