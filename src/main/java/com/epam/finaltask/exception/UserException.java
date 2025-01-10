package com.epam.finaltask.exception;

import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@Getter
public class UserException extends TravelAgencyException {
    public UserException(UUID id, MessageSource messageSource) {
        super(HttpStatus.NOT_FOUND,
                messageSource.getMessage("user.not.found.id", new Object[]{id}, LocaleContextHolder.getLocale()));
    }

    public UserException(String username, MessageSource messageSource) {
        super(HttpStatus.NOT_FOUND,
                messageSource.getMessage("user.not.found.username", new Object[]{username}, LocaleContextHolder.getLocale()));
    }
}
