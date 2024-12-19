package com.epam.finaltask.util.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUserValidator.class)
public @interface ValidUniqueField {
    String message() default "Field is already in use";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    FieldType fieldType();

    enum FieldType {
        USERNAME,
        PHONE
    }
}
