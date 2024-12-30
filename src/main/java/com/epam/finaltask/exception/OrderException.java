package com.epam.finaltask.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

public class OrderException extends TravelAgencyException {
    public OrderException(String status, MessageSource messageSource) {
        super(HttpStatus.BAD_REQUEST,
                messageSource.getMessage("order.status", new Object[]{status}, LocaleContextHolder.getLocale()));
    }

    public OrderException(Double balance, MessageSource messageSource) {
        super(HttpStatus.BAD_REQUEST,
                messageSource.getMessage("order.balance", new Object[]{balance}, LocaleContextHolder.getLocale()));
    }

}
