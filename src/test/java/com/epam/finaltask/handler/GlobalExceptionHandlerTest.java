package com.epam.finaltask.handler;

import com.epam.finaltask.exception.TravelAgencyException;
import com.epam.finaltask.service.LocalizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    @Mock
    private LocalizationService localizationService;

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNotFoundException() {
        String reason = "test.reason";
        String localizedMessage = "Localized message";
        TravelAgencyException exception = new TravelAgencyException(HttpStatus.NOT_FOUND, reason, null);

        when(localizationService.getMessage(reason, null)).thenReturn(localizedMessage);
        ResponseEntity<Map<String, Object>> response = exceptionHandler.notFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(localizedMessage, response.getBody().get("message"));
    }

    @Test
    void testHandleGlobalException() {
        Exception exception = new Exception("Unexpected error");

        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleGlobalException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unexpected error occurred", response.getBody().get("message"));
    }
}
