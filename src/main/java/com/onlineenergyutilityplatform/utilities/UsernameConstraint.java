package com.onlineenergyutilityplatform.utilities;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UsernameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameConstraint {
    String message() default "Username must be between 8 and 20 characters and contain only alphanumeric characters.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
