package com.sharom.wrm.common.exception;

import com.sharom.wrm.common.response.ErrorResponse;
import com.sharom.wrm.common.response.FieldErrorResponse;
import com.sharom.wrm.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
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

    private final MessageService messageService;

    public GlobalExceptionHandler(MessageService messageService) {
        this.messageService = messageService;
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

        String message = messageService.get("validation.error");

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

        String localizedMessage = messageService.get(ex.getMessage());

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

        String message = messageService.get("system.internal.error");

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

        String fieldKey = "field." + error.getField();

        String localizedField = messageService.get(fieldKey);
        String localizedMessage = messageService.get(error.getDefaultMessage());

        return FieldErrorResponse.builder()
                .field(localizedField)
                .code(error.getDefaultMessage())
                .message(localizedMessage)
                .build();
    }
}