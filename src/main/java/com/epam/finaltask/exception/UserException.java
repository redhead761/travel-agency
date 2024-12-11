package com.epam.finaltask.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class UserException extends TravelAgencyException {
    public UserException(UUID id) {
        super(HttpStatus.NOT_FOUND, "User with id " + id + " was not found");
    }

    public UserException(String username) {
        super(HttpStatus.NOT_FOUND, "User with username " + username + " was not found");
    }
}
