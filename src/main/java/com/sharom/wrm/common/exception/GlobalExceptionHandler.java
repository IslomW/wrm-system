package com.sharom.wrm.common.exception;

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
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<FieldErrorResponse> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::mapToFieldError)
                .toList();

        log.warn("Validation failed: path={}, errors={}", request.getRequestURI(), errors);

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                errors,
                request.getRequestURI()
        );
    }

//    @ExceptionHandler(BadRequestException.class)
//    public ResponseEntity<ErrorResponse> handleBadRequest(
//            BadRequestException ex,
//            HttpServletRequest request) {
//
//        log.warn("Bad request: path={}, message={}", request.getRequestURI(), ex.getMessage());
//
//        return buildResponse(
//                HttpStatus.BAD_REQUEST,
//                ex.getMessage(),
//                request.getRequestURI()
//        );
//    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        log.error("Not found: path={}, message={}", request.getRequestURI(), ex.getMessage());

        return buildResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                null,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unexpected error: path={}", request.getRequestURI(), ex);

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error",
                null,
                request.getRequestURI()
        );
    }


    private ResponseEntity<ErrorResponse> buildResponse(
            HttpStatus status,
            String message,
            List<?> errors,
            String path) {

        ErrorResponse response = ErrorResponse.builder()
                .status(status.value())
                .message(message)
                .errors((List<FieldErrorResponse>) errors)
                .timestamp(System.currentTimeMillis())
                .path(path)
                .build();

        return ResponseEntity.status(status).body(response);
    }


    private FieldErrorResponse mapToFieldError(FieldError error) {

        String localizedMessage = messageSource.getMessage(
                error.getDefaultMessage(),
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
