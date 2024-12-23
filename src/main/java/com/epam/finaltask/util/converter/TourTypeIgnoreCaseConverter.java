package com.epam.finaltask.util.converter;

import com.epam.finaltask.exception.EnumException;
import com.epam.finaltask.model.TourType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TourTypeIgnoreCaseConverter implements Converter<String, TourType> {
    MessageSource messageSource;

    @Override
    public TourType convert(String source) {
        try {
            return TourType.valueOf(source.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new EnumException(source,messageSource);
        }
    }
}
