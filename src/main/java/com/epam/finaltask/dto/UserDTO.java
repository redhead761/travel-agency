package com.epam.finaltask.dto;

import com.epam.finaltask.dto.group.OnUpdate;
import com.epam.finaltask.model.Role;
import com.epam.finaltask.util.validator.ValidEnum;
import com.epam.finaltask.util.validator.ValidUniqueField;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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

    @NotNull(message = "password.not.null")
    @Pattern(regexp = "[a-zA-Z0-9]{8,16}", message = "password.pattern")
    private String password;

    @NotNull(message = "username.not.null")
    @Pattern(regexp = "[a-zA-Z0-9]{3,45}", message = "username.valid")
    @ValidUniqueField(fieldType = ValidUniqueField.FieldType.USERNAME, message = "username.unique")
    private String username;

    @NotNull(groups = {OnUpdate.class})
    @ValidEnum(groups = {OnUpdate.class}, value = Role.class, message = "user.role.valid")
    private String role;

    private List<VoucherDTO> vouchers;

    @Pattern(regexp = "^\\+?[1-9][0-9]{7,14}$", message = "phone.pattern")
    @ValidUniqueField(fieldType = ValidUniqueField.FieldType.PHONE, message = "phone.unique")
    private String phoneNumber;

    @NotNull(groups = {OnUpdate.class})
    @Positive(groups = {OnUpdate.class}, message = "balance.positive")
    private Double balance;

    private boolean accountStatus;
}
