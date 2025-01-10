package com.epam.finaltask.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@Getter
public class UserException extends TravelAgencyException {
    public UserException(UUID id) {
        super(HttpStatus.NOT_FOUND, "user.not.found.id", new Object[]{id});
    }

    public UserException(String username) {
        super(HttpStatus.NOT_FOUND, "user.not.found.username", new Object[]{username});
    }
}
