package com.epam.finaltask.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestAmount {
    @NotNull(message = "amount.not.null")
    @Positive(message = "amount.positive")
    private Double amount;
}
