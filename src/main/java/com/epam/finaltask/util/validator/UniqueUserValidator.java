package com.epam.finaltask.util.validator;

import com.epam.finaltask.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UniqueUserValidator implements ConstraintValidator<ValidUniqueField, String> {
    private final UserRepository userRepository;
    private ValidUniqueField.FieldType fieldType;

    @Override
    public void initialize(ValidUniqueField constraintAnnotation) {
        this.fieldType = constraintAnnotation.fieldType();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        return switch (fieldType) {
            case USERNAME -> userRepository.doesNotExistByUsername(value);
            case PHONE -> userRepository.doesNotExistsByPhoneNumber(value);
        };
    }
}
