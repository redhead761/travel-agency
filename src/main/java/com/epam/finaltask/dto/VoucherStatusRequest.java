package com.epam.finaltask.dto;

import com.epam.finaltask.model.VoucherStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherStatusRequest {
    private VoucherStatus status;
    private int version;
}
