package com.sharom.wrm.common.util;

import com.sharom.wrm.common.constant.MessageKey;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneConstraintValidator implements ConstraintValidator<ValidPhone, String> {

    private static final Pattern CLEANUP = Pattern.compile("[\\s\\-\\(\\)]");
    private static final Pattern PHONE   = Pattern.compile("^\\+998\\d{9}$");

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if (phone == null || phone.isBlank()) {
            return true; // @NotBlank handles this
        }

        String cleaned = CLEANUP.matcher(phone).replaceAll("");

        if (!PHONE.matcher(cleaned).matches()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(MessageKey.PHONE_INVALID)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
