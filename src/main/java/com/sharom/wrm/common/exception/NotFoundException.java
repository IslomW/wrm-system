package com.sharom.wrm.common.exception;

import com.sharom.wrm.common.constant.Code;
import org.springframework.http.HttpStatus;

import static com.sharom.wrm.common.constant.MessageKey.USER_NOT_FOUND;

public class NotFoundException extends ApiException{
    public NotFoundException(String message, String code) {
        super(message, code, HttpStatus.NOT_FOUND);
    }

    public static NotFoundException userNotFound(){
        return new NotFoundException(USER_NOT_FOUND, USER_NOT_FOUND);
    }
}
