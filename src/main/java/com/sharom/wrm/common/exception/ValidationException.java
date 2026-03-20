package com.sharom.wrm.common.exception;

public class ValidationException extends RuntimeException{
    private final String messageKey;

    public ValidationException(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }

}
