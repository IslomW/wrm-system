package com.sharom.wrm.common.util;

import com.sharom.wrm.common.constant.MessageKey;
import com.sharom.wrm.common.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{}|;:'\",.<>?/]).{8,}$";



    public void validate(String password) {
        if (password == null || password.isBlank()) {
            throw new ValidationException(MessageKey.PASSWORD_EMPTY);
        }

        if (!password.matches(PASSWORD_PATTERN)) {
            determineSpecificError(password);
        }
    }

    public void determineSpecificError(String password) {

        if (password.length() < 8) {
            throw new ValidationException(MessageKey.PASSWORD_TOO_SHORT);
        }

        if (!containsUppercase(password)) {
            throw new ValidationException(MessageKey.PASSWORD_NO_UPPERCASE);
        }

        if (!containsLowercase(password)) {
            throw new ValidationException(MessageKey.PASSWORD_NO_LOWERCASE);
        }

        if (!containsDigit(password)) {
            throw new ValidationException(MessageKey.PASSWORD_NO_DIGIT  );
        }

        if (!containsSpecialChar(password)) {
            throw new ValidationException(MessageKey.PASSWORD_NO_SPECIAL_CHARACTER);
        }
    }


    private boolean containsUppercase(String s) {
        return s.chars().anyMatch(Character::isUpperCase);
    }

    private boolean containsLowercase(String s) {
        return s.chars().anyMatch(Character::isLowerCase);
    }

    private boolean containsDigit(String s) {
        return s.chars().anyMatch(Character::isDigit);
    }

    private boolean containsSpecialChar(String s) {
        return s.chars().anyMatch(ch -> "!@#$%^&*()_+-=[]{}|;:'\",.<>?/".indexOf(ch) >= 0);
    }
}
