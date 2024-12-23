package com.epam.finaltask.util.converter;

import com.epam.finaltask.exception.EnumException;
import com.epam.finaltask.model.VoucherStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherStatusIgnoreCaseConverter implements Converter<String, VoucherStatus> {
    MessageSource messageSource;

    @Override
    public VoucherStatus convert(String source) {
        try {
            return VoucherStatus.valueOf(source.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new EnumException(source, messageSource);
        }
    }
}
