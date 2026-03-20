package com.sharom.wrm.common.exception;

import com.sharom.wrm.common.constant.Code;
import lombok.Getter;

import java.util.List;

import static com.sharom.wrm.common.constant.MessageKey.*;

@Getter
public class BadRequestAlertException extends RuntimeException{

    private final Code code;
    private List<String> errors;

    public BadRequestAlertException(String message, Code code){
        super(message);
        this.code = code;
    }


    public BadRequestAlertException(List<String> errors, Code code) {
        super("Validation failed");
        this.errors = errors;
        this.code = code;
    }

    public static BadRequestAlertException of(String key, Code code){
        return new BadRequestAlertException(key, code);
    }

    public static BadRequestAlertException of(List<String> errors, Code code) {
        return new BadRequestAlertException(errors, code);
    }

    public static BadRequestAlertException userNotFound(){
        return of(USER_NOT_FOUND, Code.DATA_NOT_FOUND);
    }

    public static BadRequestAlertException userAlreadyExists() {
        return of(USER_ALREADY_EXISTS, Code.ALREADY_EXISTS);
    }

    public static BadRequestAlertException userAlreadyExistsByThisPhoneNumber() {
        return of(USERNAME_ALREADY_EXISTS_BY_PHONE, Code.ALREADY_EXISTS);
    }

    public static BadRequestAlertException usernameAlreadyExists() {
        return of(USERNAME_ALREADY_EXISTS, Code.ALREADY_EXISTS);
    }

    public static BadRequestAlertException userDisabled() {
        return of(USER_DISABLED, Code.FORBIDDEN);
    }

    public static BadRequestAlertException userNotActive() {
        return of(USER_NOT_ACTIVE, Code.FORBIDDEN);
    }

    public static BadRequestAlertException usernameEmpty() {
        return of(USERNAME_REQUIRED, Code.INVALID_DATA);
    }

    public static BadRequestAlertException usernameTooShort() {
        return of(USERNAME_TOO_SHORT, Code.INVALID_DATA);
    }

    public static BadRequestAlertException invalidPassword(List<String> errors) {
        return of(errors, Code.INVALID_DATA);
    }


    public static BadRequestAlertException emptyPassword() {
        return of(PASSWORD_REQUIRED, Code.INVALID_DATA);
    }

    public static BadRequestAlertException emptyPhone() {
        return of(PHONE_EMPTY, Code.INVALID_DATA);
    }

    public static BadRequestAlertException invalidPhone() {
        return of(PHONE_INVALID, Code.INVALID_DATA);
    }

    public static BadRequestAlertException emptyEmail() {
        return of(EMAIL_REQUIRED, Code.INVALID_DATA);
    }

    public static BadRequestAlertException invalidEmail() {
        return of(EMAIL_INVALID, Code.INVALID_DATA);
    }
}
