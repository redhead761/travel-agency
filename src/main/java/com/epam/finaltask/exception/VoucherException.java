package com.epam.finaltask.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class VoucherException extends TravelAgencyException {
    public VoucherException(UUID id) {
        super(HttpStatus.NOT_FOUND, "voucher.not.found", new Object[]{id});
    }
}
