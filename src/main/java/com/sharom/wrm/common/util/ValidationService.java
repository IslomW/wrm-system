package com.sharom.wrm.common.util;

import com.sharom.wrm.common.exception.BadRequestAlertException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.sharom.wrm.common.constant.MessageKey.*;

@Service
@RequiredArgsConstructor
public class ValidationService {

    public void validateUsername(String username) {

        if (username == null || username.isBlank()) {
            throw BadRequestAlertException.usernameEmpty();
        }

        if (username.length() < 3) {
            throw BadRequestAlertException.usernameTooShort();
        }
    }

    public void validatePassword(String password) {

        List<String> errors = new ArrayList<>();

        if (password == null || password.isBlank()) {
            throw BadRequestAlertException.emptyPassword();
        }

        if (password.length() < 8) {
            errors.add(PASSWORD_TOO_SHORT);
        }

        if (!password.matches(".*[a-z].*")) {
            errors.add(PASSWORD_NO_LOWER);
        }

        if (!password.matches(".*[A-Z].*")) {
            errors.add(PASSWORD_NO_UPPER);
        }

        if (!password.matches(".*\\d.*")) {
            errors.add(PASSWORD_NO_DIGIT);
        }

        if (!password.matches(".*[_&].*")) {
            errors.add(PASSWORD_NO_SPECIAL);
        }

        if (!errors.isEmpty()) {
            throw BadRequestAlertException.invalidPassword(errors);
        }
    }

    public void validatePhone(String phone) {

        if (phone == null || phone.isBlank()) {
            throw BadRequestAlertException.emptyPhone();
        }

        if (!phone.matches("^\\+998\\d{9}$")) {
            throw BadRequestAlertException.invalidPhone();
        }
    }

    public void validateEmail(String email) {

        if (email == null || email.isBlank()) {
            throw BadRequestAlertException.emptyEmail();
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw BadRequestAlertException.invalidEmail();
        }
    }
}
