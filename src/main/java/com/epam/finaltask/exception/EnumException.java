package com.epam.finaltask.exception;

import org.springframework.http.HttpStatus;

public class EnumException extends TravelAgencyException{
    public EnumException(String value) {
        super(HttpStatus.NOT_FOUND, "Enum value \"" + value + "\" was not found");
    }
}
