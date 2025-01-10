package com.epam.finaltask.controller.impl;

import com.epam.finaltask.dto.Credentials;
import com.epam.finaltask.dto.JwtTokenDto;
import com.epam.finaltask.auth.AuthenticationService;
import com.epam.finaltask.controller.AuthenticationController;
import com.epam.finaltask.dto.RemoteResponse;
import com.epam.finaltask.security.JwtService;
import com.epam.finaltask.service.LocalizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationControllerImpl implements AuthenticationController {

    private final AuthenticationService service;
    private final JwtService jwtService;
    private final LocalizationService localizationService;

    @Override
    @ResponseStatus(ACCEPTED)
    @PostMapping("/login")
    public RemoteResponse authenticate(@RequestBody @Valid Credentials credentials) {
        JwtTokenDto token = service.authenticate(credentials);
        return RemoteResponse.builder()
                .succeeded(true)
                .statusCode(OK.name())
                .statusMessage(localizationService.getMessage("auth.success"))
                .results(List.of(token))
                .build();
    }

    @Override
    @PostMapping("/logout")
    public void logout(@RequestHeader String authorization) {
        jwtService.saveToBlacklist(getToken(authorization));
    }

    private String getToken(String authorization) {
        return authorization.substring(7);
    }
}

