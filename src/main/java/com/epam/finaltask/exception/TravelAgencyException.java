package com.epam.finaltask.exception;

import lombok.Getter;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Getter
public class TravelAgencyException extends ResponseStatusException {

    private final String transactionId;
    private final String errorId;

    public TravelAgencyException(HttpStatus status, String message) {
        super(status, message);
        transactionId = MDC.get("transactionId");
        errorId = UUID.randomUUID().toString();
    }
}

