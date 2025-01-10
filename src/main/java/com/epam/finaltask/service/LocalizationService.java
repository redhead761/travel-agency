package com.epam.finaltask.service;

public interface LocalizationService {
    String getMessage(String message);
    String getMessage(String message, Object[] args);
}
