package com.epam.finaltask.handler;

import com.epam.finaltask.service.LocalizationService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.DisabledException;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AuthExceptionHandlerTest {

    @Mock
    private LocalizationService localizationService;

    @InjectMocks
    private AuthExceptionHandler authExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleAuthenticationException() {
        String errorMessage = "Authentication failed";
        String errorId = UUID.randomUUID().toString();
        when(localizationService.getMessage("auth.exception")).thenReturn("Authentication exception");

        AuthenticationException exception = new AuthenticationException(errorMessage) {};

        ResponseEntity<Map<String, Object>> response = authExceptionHandler.handleAuthenticationException(exception);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().containsKey("timestamp"));
        assertTrue(response.getBody().containsKey("message"));
        assertTrue(response.getBody().containsKey("errorId"));
        assertEquals("Authentication exception", response.getBody().get("message"));
    }

    @Test
    void testHandleDisabledException() {
        String errorMessage = "User disabled";
        String errorId = UUID.randomUUID().toString();
        when(localizationService.getMessage("disabled.exception")).thenReturn("User is disabled");

        DisabledException exception = new DisabledException(errorMessage);

        ResponseEntity<Map<String, Object>> response = authExceptionHandler.handleDisabledException(exception);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().containsKey("timestamp"));
        assertTrue(response.getBody().containsKey("message"));
        assertTrue(response.getBody().containsKey("errorId"));
        assertEquals("User is disabled", response.getBody().get("message"));
    }

    @Test
    void testHandleExpiredJwtException() {
        String errorMessage = "JWT expired";
        String errorId = UUID.randomUUID().toString();
        when(localizationService.getMessage("jwt.expired.exception")).thenReturn("JWT expired exception");

        ExpiredJwtException exception = new ExpiredJwtException(null, null, errorMessage);

        ResponseEntity<Map<String, Object>> response = authExceptionHandler.handleExpiredJwtException(exception);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().containsKey("timestamp"));
        assertTrue(response.getBody().containsKey("message"));
        assertTrue(response.getBody().containsKey("errorId"));
        assertEquals("JWT expired exception", response.getBody().get("message"));
    }

    @Test
    void testHandleMalformedJwtException() {
        String errorMessage = "Malformed JWT";
        String errorId = UUID.randomUUID().toString();
        when(localizationService.getMessage("token.format.exception")).thenReturn("Malformed JWT exception");

        MalformedJwtException exception = new MalformedJwtException(errorMessage);

        ResponseEntity<Map<String, Object>> response = authExceptionHandler.handleMalformedJwtException(exception);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().containsKey("timestamp"));
        assertTrue(response.getBody().containsKey("message"));
        assertTrue(response.getBody().containsKey("errorId"));
        assertEquals("Malformed JWT exception", response.getBody().get("message"));
    }

    @Test
    void testHandleSignatureException() {
        String errorMessage = "JWT signature invalid";
        String errorId = UUID.randomUUID().toString();
        when(localizationService.getMessage("invalid.signature.exception")).thenReturn("JWT signature exception");

        SignatureException exception = new SignatureException(errorMessage);

        ResponseEntity<Map<String, Object>> response = authExceptionHandler.handleSignatureException(exception);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().containsKey("timestamp"));
        assertTrue(response.getBody().containsKey("message"));
        assertTrue(response.getBody().containsKey("errorId"));
        assertEquals("JWT signature exception", response.getBody().get("message"));
    }

    @Test
    void testHandleJwtException() {
        String errorMessage = "Invalid JWT";
        String errorId = UUID.randomUUID().toString();
        when(localizationService.getMessage("invalid.jwt.exception")).thenReturn("Invalid JWT exception");

        JwtException exception = new JwtException(errorMessage);

        ResponseEntity<Map<String, Object>> response = authExceptionHandler.handleJwtException(exception);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().containsKey("timestamp"));
        assertTrue(response.getBody().containsKey("message"));
        assertTrue(response.getBody().containsKey("errorId"));
        assertEquals("Invalid JWT exception", response.getBody().get("message"));
    }
}
