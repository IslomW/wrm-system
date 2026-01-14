package com.sharom.wrm.exception;

import com.sharom.wrm.utils.Code;
import lombok.Getter;

import static com.sharom.wrm.message.MessageKey.*;
import static com.sharom.wrm.utils.Code.*;

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
        return of(USER_NOT_FOUND, DATA_NOT_FOUND);
    }

    public static BadRequestAlertException userAlreadyExists() {
        return of(USER_ALREADY_EXISTS, ALREADY_EXISTS);
    }

    public static BadRequestAlertException userDisabled() {
        return of(USER_DISABLED, FORBIDDEN);
    }

    public static BadRequestAlertException userNotActive() {
        return of(USER_NOT_ACTIVE, FORBIDDEN);
    }







}
