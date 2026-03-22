//package com.sharom.wrm.common.util;
//
//import com.sharom.wrm.common.exception.BadRequestAlertException;
//import com.sharom.wrm.modules.user.model.dto.RegisterRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.sharom.wrm.common.constant.MessageKey.*;
//
//@Service
//@RequiredArgsConstructor
//public class ValidationService {
//
//    private final PhoneValidator phoneValidator;
//    private final PasswordValidator passwordValidator;
//
//
//    public void validate(RegisterRequest request) {
//        passwordValidator.validate(request.password());
//        phoneValidator.validate(reAquest.phoneNumber());
//    }
//}
