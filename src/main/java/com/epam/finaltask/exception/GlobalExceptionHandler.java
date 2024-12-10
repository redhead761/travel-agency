package com.epam.finaltask.exception;

import com.epam.finaltask.dto.RemoteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

import static com.epam.finaltask.exception.StatusCodes.EXCEPTION;
import static com.epam.finaltask.exception.StatusCodes.INVALID_DATA;

@Slf4j

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String GLOBAL_EXCEPTION = "Global Exception";
    private static final String RESPONSE_STATUS_EXCEPTION = "TravelAgency Exception";
    private static final String LOG_MESSAGE = "ErrorId: {}, {}: {}";
    private static final String VALIDATION_ERROR = "Validation error";

    @ExceptionHandler(TravelAgencyException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public RemoteResponse notFoundException(TravelAgencyException e) {
        logError(e.getErrorId(), RESPONSE_STATUS_EXCEPTION, e.getMessage());

        return RemoteResponse.builder()
                .succeeded(false)
                .statusCode(e.getErrorCode())
                .statusMessage(e.getMessage())
                .results(null)
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RemoteResponse handleValidationExceptions(Exception e) {
        String errorId = UUID.randomUUID().toString();
        String message;
        if (e instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            message = methodArgumentNotValidException.getBindingResult()
                    .getAllErrors()
                    .stream()
                    .findFirst().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .orElse(methodArgumentNotValidException.getMessage());
        } else {
            message = e.getMessage();
        }

        logError(errorId, VALIDATION_ERROR, e.getMessage());
        return RemoteResponse.builder()
                .succeeded(false)
                .statusCode(INVALID_DATA.name())
                .statusMessage(message)
                .results(null)
                .build();
    }

    @ExceptionHandler(Exception.class)
    public RemoteResponse handleGlobalException(Exception e) {
        String errorId = UUID.randomUUID().toString();
        logError(errorId, GLOBAL_EXCEPTION, e.getMessage());

        return RemoteResponse.builder()
                .succeeded(false)
                .statusCode(EXCEPTION.name())
                .statusMessage(e.getMessage())
                .results(null)
                .build();
    }

    private void logError(String errorId, String exceptionType, String errorMessage) {
        log.error(LOG_MESSAGE, errorId, exceptionType, errorMessage);
    }
}


