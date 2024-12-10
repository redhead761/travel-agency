package com.epam.finaltask.dto;


import com.epam.finaltask.dto.group.OnChange;
import com.epam.finaltask.dto.group.OnCreate;
import com.epam.finaltask.model.Role;
import com.epam.finaltask.util.validator.ValidEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private String id;

    @NotNull(groups = {OnCreate.class}, message = "Password can't be null")
    @Pattern(groups = {OnCreate.class}, regexp = "[a-zA-Z0-9]{8,16}",
            message = "Your password must contain upper and lower case letters and numbers, " +
                    "at least 7 and maximum 30 characters.Password cannot contains spaces")
    private String password;

    @NotNull(message = "Username can't be null")
    @Pattern(regexp = "[a-zA-Z0-9 ]{3,45}",
            message = "Username must contain only characters and numbers")
    private String username;

    @NotNull(groups = {OnChange.class})
    @ValidEnum(groups = {OnChange.class}, value = Role.class)
    private String role;

    private List<VoucherDTO> vouchers;

    @Pattern(regexp = "^\\+?[1-9][0-9]{7,14}$",
            message = "Phone number must contain only numbers")
    private String phoneNumber;

    private Double balance;

    private boolean accountStatus;
}
