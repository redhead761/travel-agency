package com.epam.finaltask.exception;

import org.springframework.http.HttpStatus;

public class PasswordException extends TravelAgencyException {
    public PasswordException() {
        super(HttpStatus.UNAUTHORIZED, "Wrong password");
    }
}

