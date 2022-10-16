package com.onlineenergyutilityplatform.utilities;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    String message() default "Password must consist of at least one lowercase, at least one uppercase, at least one number and at least one special symbol.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
