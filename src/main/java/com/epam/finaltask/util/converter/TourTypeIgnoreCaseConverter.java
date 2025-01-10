package com.epam.finaltask.util.converter;

import com.epam.finaltask.model.TourType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TourTypeIgnoreCaseConverter implements Converter<String, TourType> {
    @Override
    public TourType convert(String source) {
        return TourType.valueOf(source.trim().toUpperCase());
    }
}
