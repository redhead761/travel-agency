package com.epam.finaltask.handler;

import com.epam.finaltask.service.LocalizationService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
public class AuthExceptionHandler {
    private static final String ERROR_ID = "errorId";
    private static final String TIMESTAMP = "timestamp";
    private static final String MESSAGE = "message";
    private static final String JWT_EXPIRED_EXCEPTION = "JWT expired Exception";
    private static final String JWT_SIGNATURE_EXCEPTION = "JWT signature Exception";
    private static final String MALFORMED_JWT_EXCEPTION = "Malformed JWT Exception";
    private static final String JWT_EXCEPTION = "JWT Exception";
    private static final String AUTHENTICATION_EXCEPTION = "Authentication Exception";
    private static final String LOG_MESSAGE = "ErrorId: {}, {}: {}";
    private static final String DISABLED_EXCEPTION = "Disabled Exception";

    private final LocalizationService localizationService;

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthenticationException(AuthenticationException e) {
        String errorId = UUID.randomUUID().toString();
        logError(errorId, AUTHENTICATION_EXCEPTION, e.getMessage());
        return createUnauthResponseEntity(localizationService.getMessage("auth.exception"), errorId);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<Map<String, Object>> handleDisabledException(DisabledException e) {
        String errorId = UUID.randomUUID().toString();
        logError(errorId, DISABLED_EXCEPTION, e.getMessage());
        return createUnauthResponseEntity(localizationService.getMessage("disabled.exception"), errorId);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, Object>> handleExpiredJwtException(ExpiredJwtException e) {
        String errorId = UUID.randomUUID().toString();
        logError(errorId, JWT_EXPIRED_EXCEPTION, e.getMessage());
        return createUnauthResponseEntity(localizationService.getMessage("jwt.expired.exception"), errorId);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Map<String, Object>> handleMalformedJwtException(MalformedJwtException e) {
        String errorId = UUID.randomUUID().toString();
        logError(errorId, MALFORMED_JWT_EXCEPTION, e.getMessage());
        return createUnauthResponseEntity(localizationService.getMessage("token.format.exception"), errorId);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Map<String, Object>> handleSignatureException(SignatureException e) {
        String errorId = UUID.randomUUID().toString();
        logError(errorId, JWT_SIGNATURE_EXCEPTION, e.getMessage());
        return createUnauthResponseEntity(localizationService.getMessage("invalid.signature.exception"), errorId);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Map<String, Object>> handleJwtException(JwtException e) {
        String errorId = UUID.randomUUID().toString();
        logError(errorId, JWT_EXCEPTION, e.getMessage());
        return createUnauthResponseEntity(localizationService.getMessage("invalid.jwt.exception"), errorId);
    }

    private ResponseEntity<Map<String, Object>> createUnauthResponseEntity(String message, String errorId) {
        Map<String, Object> body = Map.of(
                TIMESTAMP, LocalDateTime.now(),
                MESSAGE, message,
                ERROR_ID, errorId
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    private void logError(String errorId, String exceptionType, String errorMessage) {
        log.error(LOG_MESSAGE, errorId, exceptionType, errorMessage);
    }
}
