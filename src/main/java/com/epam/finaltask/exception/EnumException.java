package com.epam.finaltask.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

public class EnumException extends TravelAgencyException {
    public EnumException(String value, MessageSource messageSource) {
        super(HttpStatus.NOT_FOUND,
                messageSource.getMessage("enum.not.found", new Object[]{value}, LocaleContextHolder.getLocale()));
    }
}
