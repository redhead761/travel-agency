package com.epam.finaltask.util.converter;

import com.epam.finaltask.model.VoucherStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherStatusIgnoreCaseConverter implements Converter<String, VoucherStatus> {
    @Override
    public VoucherStatus convert(String source) {
        return VoucherStatus.valueOf(source.trim().toUpperCase());
    }
}
