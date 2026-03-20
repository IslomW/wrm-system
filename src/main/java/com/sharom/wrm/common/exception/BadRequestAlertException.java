package com.sharom.wrm.common.exception;

import com.sharom.wrm.common.constant.Code;
import lombok.Getter;

import static com.sharom.wrm.common.constant.MessageKey.*;

@Getter
public class BadRequestAlertException extends RuntimeException{

    private final Code code;

    public BadRequestAlertException(String message, Code code){
        super(message);
        this.code = code;
    }


    public static BadRequestAlertException of(String key, Code code){
        return new BadRequestAlertException(key, code);
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







}
