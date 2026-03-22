package com.sharom.wrm.common.util;

import com.sharom.wrm.common.constant.MessageKey;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    private static final int MIN_LENGTH = 8;

    private static final Pattern UPPERCASE   = Pattern.compile("[A-Z]");
    private static final Pattern LOWERCASE   = Pattern.compile("[a-z]");
    private static final Pattern DIGIT       = Pattern.compile("\\d");
    private static final Pattern SPECIAL     = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{}|;:'\",.<>?/]");

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        if (password == null || password.isBlank()) {
            return true;
        }

        context.disableDefaultConstraintViolation();


        if (password.length() < MIN_LENGTH) {
            return fail(context, MessageKey.PASSWORD_TOO_SHORT);
        }
        if (!UPPERCASE.matcher(password).find()) {
            return fail(context, MessageKey.PASSWORD_NO_UPPERCASE);
        }
        if (!LOWERCASE.matcher(password).find()) {
            return fail(context, MessageKey.PASSWORD_NO_LOWERCASE);
        }
        if (!DIGIT.matcher(password).find()) {
            return fail(context, MessageKey.PASSWORD_NO_DIGIT);
        }
        if (!SPECIAL.matcher(password).find()) {
            return fail(context, MessageKey.PASSWORD_NO_SPECIAL_CHARACTER);
        }

        return true;
    }

    private boolean fail(ConstraintValidatorContext context, String message) {
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        return false;
    }
}
