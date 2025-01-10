package com.epam.finaltask.util.converter;

import com.epam.finaltask.exception.EnumException;
import com.epam.finaltask.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleIgnoreCaseConverter implements Converter<String, Role> {
    private final MessageSource messageSource;

    @Override
    public Role convert(String source) {
        try {
            return Role.valueOf(source.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new EnumException(source, messageSource);
        }
    }
}
