package com.epam.finaltask.exception;

import com.epam.finaltask.dto.RemoteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.epam.finaltask.exception.StatusCodes.EXCEPTION;
import static com.epam.finaltask.exception.StatusCodes.INVALID_DATA;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String TIMESTAMP = "timestamp";
    private static final String MESSAGE = "message";
    private static final String GLOBAL_EXCEPTION = "Global Exception";
    private static final String UNEXPECTED_ERROR_OCCURRED = "Unexpected error occurred";
    private static final String ERROR_ID = "errorId";
    private static final String RESPONSE_STATUS_EXCEPTION = "TravelAgency Exception";
    private static final String LOG_MESSAGE = "ErrorId: {}, {}: {}";
    private static final String VALIDATION_ERROR = "Validation error";

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

        Map<String, Object> body = new HashMap<>(Map.of(TIMESTAMP, LocalDateTime.now(), ERROR_ID, errorId));
        body.putAll(e.getFieldErrors().stream()
                .collect(Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList()))));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception e) {
        String errorId = UUID.randomUUID().toString();
        logError(errorId, GLOBAL_EXCEPTION, e.getMessage());

        return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, UNEXPECTED_ERROR_OCCURRED, errorId);
    }

    private String getMessage(MethodArgumentTypeMismatchException e) {
        String paramName = e.getName();
        String paramValue = e.getValue() == null ? "null" : e.getValue().toString();
        String requiredType = e.getRequiredType() != null ? e.getRequiredType().getSimpleName() : "Unknown";

        return String.format("Invalid value '%s' for parameter '%s'. Expected type: %s.",
                paramValue, paramName, requiredType);
    }

    private void logError(String errorId, String exceptionType, String errorMessage) {
        log.error(LOG_MESSAGE, errorId, exceptionType, errorMessage);
    }

    private ResponseEntity<Map<String, Object>> createResponseEntity(HttpStatusCode status,
                                                                     String message,
                                                                     String errorId) {
        Map<String, Object> body = Map.of(
                TIMESTAMP, LocalDateTime.now(),
                MESSAGE, message,
                ERROR_ID, errorId
        );

        return ResponseEntity.status(status).body(body);
    }
}


