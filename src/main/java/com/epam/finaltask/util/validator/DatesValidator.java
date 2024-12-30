package com.epam.finaltask.util.validator;

import com.epam.finaltask.dto.VoucherDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DatesValidator implements ConstraintValidator<ValidDates, VoucherDTO> {
    @Override
    public boolean isValid(VoucherDTO voucherDTO, ConstraintValidatorContext constraintValidatorContext) {
        if (voucherDTO.getArrivalDate() == null || voucherDTO.getEvictionDate() == null) {
            return true;
        }
        return voucherDTO.getArrivalDate().isBefore(voucherDTO.getEvictionDate()) ||
                voucherDTO.getEvictionDate().isAfter(voucherDTO.getArrivalDate());
    }
}
