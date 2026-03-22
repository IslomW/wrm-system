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


    public static final String VERIFICATION_CODE_INVALID ="invalid.verification.code";
    public static final String TOO_MANY_REQUESTS ="please.wait.before.requesting.a.new.code";
    public static final String RESET_PASSWORD_NOT_ALLOWED ="reset.password.not.allowed";
    public static final String EXPIRED_CODE ="expired.code";


    /* === Registration === */
    public static final String REGISTRATION_FAILED = "registration.failed";
    public static final String PASSWORD_TOO_SHORT = "password.too.short";
    public static final String PASSWORD_NO_DIGIT = "password.no.digit";
    public static final String PASSWORD_NO_UPPERCASE = "password.no.uppercase";
    public static final String PASSWORD_NO_LOWERCASE = "password.no.uppercase";
    public static final String PASSWORD_NO_SPECIAL_CHARACTER = "password.no.special.character";
    public static final String USERNAME_NOT_BLANK = "username.not.blank";
    public static final String USERNAME_SIZE = "username.size.error";
    public static final String USER_TYPE_REQUIRED = "user.type.required";
    public static final String PASSWORD_INVALID = "password.invalid";
    public static final String PASSWORD_NOT_BLANK = "password.not.blank";
    public static final String PASSWORD_EMPTY = "password.empty";
    public static final String PHONE_NOT_BLANK = "phone.not.blank";
    public static final String PHONE_INVALID = "phone.invalid";
    public static final String EMAIL_NOT_BLANK = "email.not.blank";
    public static final String EMAIL_INVALID = "email.invalid";

    public static final String USERNAME_REQUIRED = "username.required";
    public static final String PASSWORD_REQUIRED = "password.required";
    public static final String USERNAME_TOO_SHORT = "username.must.be.at.least.3.characters";
    public static final String PHONE_EMPTY = "phone.required";
    public static final String EMAIL_REQUIRED = "email.must.not.be.empty";


    public static final String BOX_GROUP_NOT_FOUND = "box.group.not.found";
    public static final String BOX_NOT_FOUND = "box.not.found";

    public static final String WAREHOUSE_INACTIVE = "warehouse.inactive";
    public static final String WAREHOUSE_NOT_FOUND = "warehouse.already.exists";

    /* === Validation === */
    public static final String VALIDATION_ERROR = "validation.error";
    public static final String FIELD_REQUIRED = "validation.field.required";
    public static final String FIELD_INVALID = "validation.field.invalid";

    /* === System === */
    public static final String INTERNAL_ERROR = "system.internal.error";
    public static final String ACCESS_DENIED = "system.access.denied";

    public static final String ERROR_SEND_CODE_TO_EMAIL = "error.sent.code.to.email";}

