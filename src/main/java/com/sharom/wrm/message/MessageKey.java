package com.sharom.wrm.message;

import lombok.Getter;

@Getter
public final class MessageKey {

    private MessageKey() {
    }

    /* === User === */
    public static final String USER_NOT_FOUND = "user.not.found";
    public static final String USER_ALREADY_EXISTS = "user.already.exists";
    public static final String USER_DISABLED = "user.disabled";
    public static final String USER_DELETED = "user.deleted";
    public static final String USER_NOT_ACTIVE = "user.not.active";



    /* === Registration === */
    public static final String REGISTRATION_FAILED = "registration.failed";
    public static final String PASSWORD_TOO_SHORT = "registration.password.too.short";
    public static final String USERNAME_REQUIRED = "registration.username.required";
    public static final String PASSWORD_REQUIRED = "registration.password.required";
    public static final String USER_TYPE_REQUIRED = "registration.user.type.required";



    /* === Validation === */
    public static final String VALIDATION_ERROR = "validation.error";
    public static final String FIELD_REQUIRED = "validation.field.required";
    public static final String FIELD_INVALID = "validation.field.invalid";

    /* === System === */
    public static final String INTERNAL_ERROR = "system.internal.error";
    public static final String ACCESS_DENIED = "system.access.denied";
}

