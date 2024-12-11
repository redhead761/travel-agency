package com.epam.finaltask.util.converter;

import com.epam.finaltask.exception.EnumException;
import com.epam.finaltask.model.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleIgnoreCaseConverter implements Converter<String, Role> {

    @Override
    public Role convert(String source) {
        try {
            return Role.valueOf(source.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new EnumException(source);
        }
    }
}
