package com.epam.finaltask.exception;

import org.springframework.http.HttpStatus;

public class PhoneNumberAlreadyExistsException extends TravelAgencyException {
    public PhoneNumberAlreadyExistsException(String phoneNumber) {
        super(HttpStatus.CONFLICT, "Phone number " + phoneNumber + " is already exist");
    }
}
