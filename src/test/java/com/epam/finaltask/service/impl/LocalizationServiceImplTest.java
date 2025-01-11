package com.epam.finaltask.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LocalizationServiceImplTest {

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private LocalizationServiceImpl localizationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        LocaleContextHolder.setLocale(java.util.Locale.ENGLISH);
    }

    @Test
    void testGetMessageWithoutArgs() {
        String messageCode = "test.message";
        String expectedMessage = "Test Message";

        when(messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale())).thenReturn(expectedMessage);

        String actualMessage = localizationService.getMessage(messageCode);

        assertEquals(expectedMessage, actualMessage);
        verify(messageSource, times(1)).getMessage(messageCode, null, LocaleContextHolder.getLocale());
    }

    @Test
    void testGetMessageWithArgs() {
        String messageCode = "test.message.withArgs";
        Object[] args = new Object[] {"arg1", "arg2"};
        String expectedMessage = "Test Message with args: arg1, arg2";

        when(messageSource.getMessage(messageCode, args, LocaleContextHolder.getLocale())).thenReturn(expectedMessage);

        String actualMessage = localizationService.getMessage(messageCode, args);

        assertEquals(expectedMessage, actualMessage);
        verify(messageSource, times(1)).getMessage(messageCode, args, LocaleContextHolder.getLocale());
    }
}
