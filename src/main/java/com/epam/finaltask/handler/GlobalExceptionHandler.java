package com.epam.finaltask.handler;

import com.epam.finaltask.exception.TravelAgencyException;
import com.epam.finaltask.service.LocalizationService;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
    private static final String OPTIMISTIC_LOCK_EXCEPTION = "Optimistic lock exception";

    private final LocalizationService localizationService;

    @ExceptionHandler(TravelAgencyException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> notFoundException(TravelAgencyException e) {
        logError(e.getErrorId(), RESPONSE_STATUS_EXCEPTION, e.getReason());
        String message = localizationService.getMessage(e.getReason(), e.getArgs());
        return createResponseEntity(e.getStatusCode(), message, e.getErrorId());
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String errorId = UUID.randomUUID().toString();
        String message = getMessage(e);
        logError(errorId, VALIDATION_ERROR, e.getMessage());
        return createResponseEntity(HttpStatus.BAD_REQUEST, message, errorId);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception e) {
        String errorId = UUID.randomUUID().toString();
        logError(errorId, GLOBAL_EXCEPTION, e.getMessage());
        return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, UNEXPECTED_ERROR_OCCURRED, errorId);
    }

    @ExceptionHandler(OptimisticLockException.class)
    public ResponseEntity<Map<String, Object>> handleOptimisticLockException(OptimisticLockException e) {
        String errorId = UUID.randomUUID().toString();
        logError(errorId, OPTIMISTIC_LOCK_EXCEPTION, e.getMessage());
        return createResponseEntity(HttpStatus.CONFLICT, localizationService.getMessage("conflict"), errorId);
    }

    private String getMessage(HttpMessageNotReadableException e) {
        InvalidFormatException cause = (InvalidFormatException) e.getCause();
        String fieldName = cause.getPath().get(0).getFieldName();
        Object invalidValue = cause.getValue();
        return localizationService.getMessage("invalid.value", new Object[]{fieldName, invalidValue});
    }

    private void getGlobalErrors(MethodArgumentNotValidException e, Map<String, Object> body) {
        List<String> globalErrors = e.getGlobalErrors().stream()
                .map(objectError -> localizationService.getMessage(Objects.requireNonNull(objectError.getDefaultMessage())))
                .toList();
        if (!globalErrors.isEmpty()) body.put("date", globalErrors);
    }

    private void getFieldsErrors(MethodArgumentNotValidException e, Map<String, Object> body) {
        body.putAll(e.getFieldErrors().stream()
                .collect(Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(
                                fieldError -> localizationService.getMessage(
                                        fieldError.getDefaultMessage(), new Object[]{fieldError.getRejectedValue()}),
                                Collectors.toList()))));
    }

    private String getMessage(MethodArgumentTypeMismatchException e) {
        String paramName = e.getName();
        String paramValue = Objects.requireNonNull(e.getValue()).toString();
        String requiredType = Objects.requireNonNull(e.getRequiredType()).getSimpleName();
        return localizationService.getMessage("enum.not.found.exception", new Object[]{paramValue, paramName, requiredType});
    }

    private void logError(String errorId, String exceptionType, String errorMessage) {
        log.error(LOG_MESSAGE, errorId, exceptionType, errorMessage);
    }

    private ResponseEntity<Map<String, Object>> createResponseEntity(HttpStatusCode status, String message, String errorId) {
        Map<String, Object> body = Map.of(TIMESTAMP, LocalDateTime.now(), MESSAGE, message, ERROR_ID, errorId);
        return ResponseEntity.status(status).body(body);
    }
}
