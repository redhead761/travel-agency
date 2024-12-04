package com.epam.finaltask.exception;

public class EntityNotFoundException extends TravelAgencyException{
    public EntityNotFoundException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}

