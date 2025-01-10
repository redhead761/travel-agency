package com.epam.finaltask.util.converter;

import com.epam.finaltask.model.HotelType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class HotelTypeIgnoreCaseConverter implements Converter<String, HotelType> {
    @Override
    public HotelType convert(String source) {
        return HotelType.valueOf(source.trim().toUpperCase());
    }
}
