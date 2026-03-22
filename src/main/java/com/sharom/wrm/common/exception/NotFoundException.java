package com.sharom.wrm.common.exception;

import com.sharom.wrm.common.constant.Code;
import org.springframework.http.HttpStatus;

import static com.sharom.wrm.common.constant.MessageKey.*;

public class NotFoundException extends ApiException {

    private static final String LOG_MESSAGE = "Not Found";

    @Override
    public String getLogMessage() {
        return LOG_MESSAGE;
    }

    public NotFoundException(String message, String code) {
        super(message, code, HttpStatus.NOT_FOUND);
    }

    public static NotFoundException userNotFound() {
        return new NotFoundException(USER_NOT_FOUND, USER_NOT_FOUND);
    }

    public static NotFoundException groupNotFound() {
        return new NotFoundException(BOX_GROUP_NOT_FOUND, BOX_GROUP_NOT_FOUND);
    }

    public static NotFoundException warehouseNotFound() {return new NotFoundException(WAREHOUSE_NOT_FOUND, WAREHOUSE_NOT_FOUND);}

    public static NotFoundException boxNotFound() {
        return new NotFoundException(BOX_NOT_FOUND, BOX_NOT_FOUND);
    }
}
