package com.sharom.wrm.common.exception;

import com.sharom.wrm.common.response.ErrorResponse;
import com.sharom.wrm.common.response.FieldErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<FieldErrorResponse> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::mapToFieldError)
                .toList();

        String message = messageSource.getMessage(
                "validation.error",
                null,
                "Validation error",
                LocaleContextHolder.getLocale()
        );

        log.warn("Validation failed: path={}, errors={}", request.getRequestURI(), errors);

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                message,
                errors,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(
            ApiException ex,
            HttpServletRequest request) {

        log.warn("{}: path={}, message={}, code={}",
                ex.getLogMessage(),
                request.getRequestURI(),
                ex.getMessage(),
                ex.getCode());

        String localizedMessage = messageSource.getMessage(
                ex.getMessage(), // bu key
                null,
                ex.getMessage(), // fallback
                LocaleContextHolder.getLocale()
        );

        return buildResponse(
                ex.getStatus(),
                localizedMessage,
                null,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unexpected error: path={}", request.getRequestURI(), ex);

        String message = messageSource.getMessage(
                "system.internal.error",
                null,
                "Internal server error",
                LocaleContextHolder.getLocale()
        );

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                message,
                null,
                request.getRequestURI()
        );
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            HttpStatus status,
            String message,
            List<FieldErrorResponse> errors,
            String path) {

        ErrorResponse response = ErrorResponse.builder()
                .status(status.value())
                .message(message)
                .errors(errors == null ? List.of() : errors)
                .timestamp(System.currentTimeMillis())
                .path(path)
                .build();

        return ResponseEntity.status(status).body(response);
    }


    private FieldErrorResponse mapToFieldError(FieldError error) {

        String localizedMessage = messageSource.getMessage(
                error.getDefaultMessage(), // key
                null,
                error.getDefaultMessage(),
                LocaleContextHolder.getLocale()
        );

        return FieldErrorResponse.builder()
                .field(error.getField())
                .code(error.getDefaultMessage())
                .message(localizedMessage)
                .build();
    }
}