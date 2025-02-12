package com.epam.finaltask.service.impl;

import com.epam.finaltask.dto.Credentials;
import com.epam.finaltask.dto.JwtTokenDto;
import com.epam.finaltask.service.AuthenticationService;
import com.epam.finaltask.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtTokenDto authenticate(Credentials credentials) {
        UserDetails userDetails = getUserDetails(credentials);
        String jwtToken = jwtService.generateToken(userDetails);
        return JwtTokenDto.builder().accessToken(jwtToken).build();
    }

    private UserDetails getUserDetails(Credentials credentials) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));
        return (UserDetails) authenticate.getPrincipal();
    }
}
