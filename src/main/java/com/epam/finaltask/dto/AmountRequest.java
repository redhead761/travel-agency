package com.epam.finaltask.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmountRequest {
    @NotNull(message = "amount.not.null")
    @Positive(message = "amount.positive")
    private Double amount;
}
