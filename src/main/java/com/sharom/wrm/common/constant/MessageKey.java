package com.sharom.wrm.common.constant;

import lombok.Getter;

@Getter
public final class MessageKey {

    private MessageKey() {
    }

    /* === User === */
    public static final String USER_NOT_FOUND = "user.not.found";
    public static final String USER_ALREADY_EXISTS = "user.already.exists";
    public static final String USERNAME_ALREADY_EXISTS = "username.already.exists";
    public static final String USERNAME_ALREADY_EXISTS_BY_PHONE = "user.already.exists.by.phone";
    public static final String USER_DISABLED = "user.disabled";
    public static final String USER_DELETED = "user.deleted";
    public static final String USER_NOT_ACTIVE = "user.not.active";



    /* === Registration === */
    public static final String REGISTRATION_FAILED = "registration.failed";
    public static final String PASSWORD_TOO_SHORT = "registration.password.too.short";
    public static final String USERNAME_REQUIRED = "registration.username.required";
    public static final String PASSWORD_REQUIRED = "registration.password.required";
    public static final String USER_TYPE_REQUIRED = "registration.user.type.required";
    public static final String INVALID_PASSWORD = "registration.invalid.password";
    public static final String USERNAME_INVALID_FORMAT = "registration.username.invalid.format";
    public static final String USERNAME_TOO_SHORT = "registration.username.must.be.at.least.3.characters";
    public static final String PASSWORD_NO_LOWER = "registration.password.no.lowercase";
    public static final String PASSWORD_NO_UPPER = "registration.password.no.uppercase";
    public static final String PASSWORD_NO_DIGIT = "registration.password.no.digit";
    public static final String PASSWORD_NO_SPECIAL = "registration.password.no.special";
    public static final String PHONE_EMPTY = "registration.phone.required";
    public static final String PHONE_INVALID = "registration.phone.invalid.format.Expected:+998901234567";
    public static final String EMAIL_REQUIRED = "registration.email.must.not.be.empty";
    public static final String EMAIL_INVALID = "registration.email.invalid.format";


    /* === Validation === */
    public static final String VALIDATION_ERROR = "validation.error";
    public static final String FIELD_REQUIRED = "validation.field.required";
    public static final String FIELD_INVALID = "validation.field.invalid";

    /* === System === */
    public static final String INTERNAL_ERROR = "system.internal.error";
    public static final String ACCESS_DENIED = "system.access.denied";
}

