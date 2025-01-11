package com.epam.finaltask.service;

import com.epam.finaltask.dto.Credentials;
import com.epam.finaltask.dto.JwtTokenDto;

public interface AuthenticationService {
    JwtTokenDto authenticate(Credentials credentials);
}
