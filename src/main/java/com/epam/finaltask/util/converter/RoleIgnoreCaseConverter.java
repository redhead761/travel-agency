package com.epam.finaltask.util.converter;

import com.epam.finaltask.model.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleIgnoreCaseConverter implements Converter<String, Role> {
    @Override
    public Role convert(String source) {
            return Role.valueOf(source.trim().toUpperCase());
    }
}
