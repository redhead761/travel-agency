package com.epam.finaltask.service.impl;

import com.epam.finaltask.service.LocalizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocalizationServiceImpl implements LocalizationService {

    private final MessageSource messageSource;

    @Override
    public String getMessage(String message) {
        return messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
    }

    @Override
    public String getMessage(String message, Object[] args) {
        return messageSource.getMessage(message, args, LocaleContextHolder.getLocale());
    }
}

