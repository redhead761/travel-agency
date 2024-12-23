package com.epam.finaltask.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Getter
public class TravelAgencyException extends ResponseStatusException {

    private final String transactionId;
    private final String errorId;

    private static final String TRANSACTION_ID = "transactionId";

    public TravelAgencyException(HttpStatus status, String message) {
        super(status, message);
        transactionId = MDC.get(TRANSACTION_ID);
        errorId = UUID.randomUUID().toString();
    }
}

