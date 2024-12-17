package com.epam.finaltask.exception;

import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends TravelAgencyException {
    public EntityAlreadyExistsException(String username) {
        super(HttpStatus.CONFLICT, "Username" + username + " is already exist");
    }
}

