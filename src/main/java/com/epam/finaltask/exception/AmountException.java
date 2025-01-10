package com.epam.finaltask.exception;

import org.springframework.http.HttpStatus;

public class AmountException extends TravelAgencyException {
    public AmountException() {
        super(HttpStatus.BAD_REQUEST, "amount.positive", null);
    }
}
