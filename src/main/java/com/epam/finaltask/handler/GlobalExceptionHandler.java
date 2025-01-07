package com.epam.finaltask.handler;

import com.epam.finaltask.exception.TravelAgencyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final String TIMESTAMP = "timestamp";
    private static final String MESSAGE = "message";
    private static final String GLOBAL_EXCEPTION = "Global Exception";
    private static final String UNEXPECTED_ERROR_OCCURRED = "Unexpected error occurred";
    private static final String ERROR_ID = "errorId";
    private static final String RESPONSE_STATUS_EXCEPTION = "TravelAgency Exception";
    private static final String LOG_MESSAGE = "ErrorId: {}, {}: {}";
    private static final String VALIDATION_ERROR = "Validation error";
    private final MessageSource messageSource;

    @ExceptionHandler(TravelAgencyException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> notFoundException(TravelAgencyException e) {
        logError(e.getErrorId(), RESPONSE_STATUS_EXCEPTION, e.getReason());
        return createResponseEntity(e.getStatusCode(), e.getReason(), e.getErrorId());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        String errorId = UUID.randomUUID().toString();
        String message = getMessage(e);
        logError(errorId, VALIDATION_ERROR, message);
        return createResponseEntity(HttpStatus.BAD_REQUEST, message, errorId);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException e) {
        String errorId = UUID.randomUUID().toString();
        logError(errorId, VALIDATION_ERROR, e.getMessage());
        Map<String, Object> body = new LinkedHashMap<>(Map.of(TIMESTAMP, LocalDateTime.now(), ERROR_ID, errorId));
        getFieldsErrors(e, body);
        getGlobalErrors(e, body);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception e) {
        String errorId = UUID.randomUUID().toString();
        logError(errorId, GLOBAL_EXCEPTION, e.getMessage());
        return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, UNEXPECTED_ERROR_OCCURRED, errorId);
    }

    private void getGlobalErrors(MethodArgumentNotValidException e, Map<String, Object> body) {
        List<String> globalErrors = e.getGlobalErrors().stream()
                .map(objectError ->
                        messageSource.getMessage(Objects.requireNonNull(objectError.getDefaultMessage()),
                                null,
                                LocaleContextHolder.getLocale()))
                .toList();
        if (!globalErrors.isEmpty()) body.put("validate class errors", globalErrors);
    }

    private void getFieldsErrors(MethodArgumentNotValidException e, Map<String, Object> body) {
        body.putAll(e.getFieldErrors().stream()
                .collect(Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(
                                fieldError -> getLocalizedMessage(fieldError.getDefaultMessage(), fieldError.getRejectedValue()),
                                Collectors.toList()))));
    }

    private String getMessage(MethodArgumentTypeMismatchException e) {
        String paramName = e.getName();
        String paramValue = (e.getValue() != null) ? e.getValue().toString() : "null";
        String requiredType = (e.getRequiredType() != null) ? e.getRequiredType().getSimpleName() : "Unknown";
        return messageSource.getMessage("enum.not.found.exception",
                new Object[]{paramValue, paramName, requiredType},
                LocaleContextHolder.getLocale());
    }

    private void logError(String errorId, String exceptionType, String errorMessage) {
        log.error(LOG_MESSAGE, errorId, exceptionType, errorMessage);
    }

    private ResponseEntity<Map<String, Object>> createResponseEntity(HttpStatusCode status, String message, String errorId) {
        Map<String, Object> body = Map.of(TIMESTAMP, LocalDateTime.now(), MESSAGE, message, ERROR_ID, errorId);
        return ResponseEntity.status(status).body(body);
    }

    private String getLocalizedMessage(String message, Object args) {
        return messageSource.getMessage(message, new Object[]{args}, LocaleContextHolder.getLocale());
    }
}


