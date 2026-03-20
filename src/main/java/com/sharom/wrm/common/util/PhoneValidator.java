package com.sharom.wrm.common.util;

import com.sharom.wrm.common.constant.MessageKey;
import com.sharom.wrm.common.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class PhoneValidator {


    public void validate(String phone) {
        if (phone == null) {
            throw new ValidationException(MessageKey.PHONE_INVALID);
        }

        String cleanedPhone = phone.replaceAll("[\\s\\-\\(\\)]", "");

        if (!cleanedPhone.matches("^\\+998\\d{9}$")) {
            throw new ValidationException(MessageKey.PHONE_INVALID);
        }
    }
}
