package com.epam.finaltask.controller.impl;

import com.epam.finaltask.dto.Credentials;
import com.epam.finaltask.dto.JwtTokenDto;
import com.epam.finaltask.dto.RemoteResponse;
import com.epam.finaltask.service.AuthenticationService;
import com.epam.finaltask.service.JwtService;
import com.epam.finaltask.service.LocalizationService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthenticationControllerImplTest {

    @InjectMocks
    private AuthenticationControllerImpl authenticationController;

    @Mock
    private AuthenticationService service;

    @Mock
    private JwtService jwtService;

    @Mock
    private LocalizationService localizationService;

    private Credentials credentials;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        credentials = new Credentials("testuser", "password");
    }

    @Test
    void testAuthenticate_Success() {
        JwtTokenDto jwtToken = new JwtTokenDto("dummyToken");
        when(service.authenticate(credentials)).thenReturn(jwtToken);
        when(localizationService.getMessage("successfully.auth")).thenReturn("Authentication successful");

        RemoteResponse response = authenticationController.authenticate(credentials);

        assertNotNull(response);
        assertTrue(response.isSucceeded());
        assertEquals(HttpStatus.OK.name(), response.getStatusCode());
        assertEquals("Authentication successful", response.getStatusMessage());
        assertNotNull(response.getResults());
        assertEquals(1, response.getResults().size());
        assertEquals(jwtToken, response.getResults().get(0));

        verify(service).authenticate(credentials);
        verify(localizationService).getMessage("successfully.auth");
    }

    @Test
    void testAuthenticate_Failure_InvalidCredentials() {
        when(service.authenticate(credentials)).thenThrow(new ValidationException("Invalid credentials"));

        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            authenticationController.authenticate(credentials);
        });

        assertEquals("Invalid credentials", thrown.getMessage());
        verify(service).authenticate(credentials);
    }

    @Test
    void testLogout_Success() {
        String authorizationHeader = "Bearer token123";
        String token = "token123";

        authenticationController.logout(authorizationHeader);

        verify(jwtService).saveToBlacklist(token);
    }
}
