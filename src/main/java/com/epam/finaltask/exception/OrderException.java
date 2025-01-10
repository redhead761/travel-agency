package com.epam.finaltask.exception;

import org.springframework.http.HttpStatus;

public class OrderException extends TravelAgencyException {
    public OrderException(String status) {
        super(HttpStatus.BAD_REQUEST, "order.status", new Object[]{status});
    }

    public OrderException(Double balance) {
        super(HttpStatus.BAD_REQUEST, "order.balance", new Object[]{balance});
    }
}
