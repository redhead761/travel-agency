package com.epam.finaltask.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Credentials {

    @NotNull(message = "Username is required")
    String username;

    @NotNull(message = "Password is required")
    String password;
}
