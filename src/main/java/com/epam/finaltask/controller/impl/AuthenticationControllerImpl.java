package com.epam.finaltask.controller.impl;

import com.epam.finaltask.dto.Credentials;
import com.epam.finaltask.auth.AuthenticationResponse;
import com.epam.finaltask.auth.AuthenticationService;
import com.epam.finaltask.controller.AuthenticationController;
import com.epam.finaltask.dto.RemoteResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthenticationControllerImpl implements AuthenticationController {

    private final AuthenticationService service;
    private static final String SUCCESSFULLY_AUTHENTICATE = "User is successfully authenticated";

    @Override
    @PostMapping("/login")
    public ResponseEntity<RemoteResponse> authenticate(
            @RequestBody @Valid Credentials credentials) {
        ResponseEntity<AuthenticationResponse> response = ResponseEntity.ok(service.authenticate(credentials));
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(RemoteResponse.builder()
                        .succeeded(true)
                        .statusCode(OK.name())
                        .statusMessage(SUCCESSFULLY_AUTHENTICATE)
                        .results(List.of(response.getBody()))
                        .build());
    }

    @Override
    @PostMapping("/logout")
    public void logout(String authorization) {

    }
}

