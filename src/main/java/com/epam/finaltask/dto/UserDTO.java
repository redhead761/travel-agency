package com.epam.finaltask.dto;


import com.epam.finaltask.dto.group.OnChange;
import com.epam.finaltask.dto.group.OnCreate;
import com.epam.finaltask.model.Role;
import com.epam.finaltask.util.validator.ValidEnum;
import com.epam.finaltask.util.validator.ValidUniqueField;
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

    @NotNull(groups = {OnCreate.class}, message = "password.not.null")
    @Pattern(groups = {OnCreate.class}, regexp = "[a-zA-Z0-9]{8,16}",
            message = "password.pattern")
    private String password;

    @NotNull(message = "Username can't be null")
    @Pattern(regexp = "[a-zA-Z0-9]{3,45}",
            message = "Username must contain only characters and numbers at least 3 and maximum 45 characters ")
    @ValidUniqueField(fieldType = ValidUniqueField.FieldType.USERNAME,
            message = "Username must be unique")
    private String username;

    @NotNull(groups = {OnChange.class})
    @ValidEnum(groups = {OnChange.class}, value = Role.class,
            message = "Invalid value ${validatedValue} for parameter role. Expected type: Role.")
    private String role;

    private List<VoucherDTO> vouchers;

    @Pattern(regexp = "^\\+?[1-9][0-9]{7,14}$",
            message = "Phone number must contain only numbers")
    @ValidUniqueField(fieldType = ValidUniqueField.FieldType.PHONE,
            message = "Phone number must be unique")
    private String phoneNumber;

    private Double balance;

    private boolean accountStatus;
}
