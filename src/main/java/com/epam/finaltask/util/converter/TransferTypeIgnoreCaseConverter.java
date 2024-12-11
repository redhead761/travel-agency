package com.epam.finaltask.util.converter;


import com.epam.finaltask.exception.EnumException;
import com.epam.finaltask.model.TransferType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TransferTypeIgnoreCaseConverter implements Converter<String, TransferType> {

    @Override
    public TransferType convert(String source) {
        try {
            return TransferType.valueOf(source.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new EnumException(source);
        }
    }
}
