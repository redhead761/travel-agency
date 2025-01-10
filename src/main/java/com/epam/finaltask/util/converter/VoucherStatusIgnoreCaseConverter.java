package com.epam.finaltask.util.converter;

import com.epam.finaltask.model.VoucherStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VoucherStatusIgnoreCaseConverter implements Converter<String, VoucherStatus> {
    @Override
    public VoucherStatus convert(String source) {
        return VoucherStatus.valueOf(source.trim().toUpperCase());
    }
}
