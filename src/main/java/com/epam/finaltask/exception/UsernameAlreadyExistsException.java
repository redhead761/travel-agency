package com.epam.finaltask.exception;

import org.springframework.http.HttpStatus;

public class UsernameAlreadyExistsException extends TravelAgencyException {
    public UsernameAlreadyExistsException(String username) {
        super(HttpStatus.CONFLICT, "Username " + username + " is already exist");
    }
}

