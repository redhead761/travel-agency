package com.epam.finaltask.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
public class EntityAlreadyExistsException extends TravelAgencyException{
    public EntityAlreadyExistsException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}

