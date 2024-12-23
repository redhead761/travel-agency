package com.epam.finaltask.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class VoucherException extends TravelAgencyException {
    public VoucherException(UUID id, MessageSource messageSource) {
        super(HttpStatus.NOT_FOUND,
                messageSource.getMessage("voucher.not.found", new Object[]{id}, LocaleContextHolder.getLocale()));
    }
}
