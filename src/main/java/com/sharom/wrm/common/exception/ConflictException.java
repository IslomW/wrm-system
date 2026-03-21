package com.sharom.wrm.common.exception;

import org.springframework.http.HttpStatus;

import static com.sharom.wrm.common.constant.MessageKey.*;

public class ConflictException extends ApiException {
    public ConflictException(String message, String code) {
        super(message, code, HttpStatus.CONFLICT);
    }


    public static ConflictException userAlreadyExists() {
        return new ConflictException(USER_ALREADY_EXISTS, USER_ALREADY_EXISTS);
    }

    public static ConflictException userAlreadyExistsByThisPhoneNumber() {
        return new ConflictException(USERNAME_ALREADY_EXISTS_BY_PHONE, USERNAME_ALREADY_EXISTS_BY_PHONE);
    }

    public static ConflictException usernameAlreadyExists() {
        return new ConflictException(USERNAME_ALREADY_EXISTS, USERNAME_ALREADY_EXISTS);
    }


}
