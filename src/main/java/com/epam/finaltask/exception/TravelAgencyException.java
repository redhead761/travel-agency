package com.epam.finaltask.exception;

import lombok.Getter;
import org.slf4j.MDC;

import java.util.UUID;

@Getter
public class TravelAgencyException extends RuntimeException {
    private String errorCode;
    private final String transactionId;
    private final String errorId;

    public TravelAgencyException(String message) {
        super(message);
        transactionId = MDC.get("transactionId");
        errorId = UUID.randomUUID().toString();
    }

    public TravelAgencyException(String errorCode, String errorMessage) {
        this(errorMessage);
        this.errorCode = errorCode;
    }
}

