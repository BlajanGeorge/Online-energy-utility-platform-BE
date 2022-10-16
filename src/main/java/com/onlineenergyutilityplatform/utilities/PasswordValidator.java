package com.onlineenergyutilityplatform.utilities;


import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    private static final String REGEX = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=])"
            + "(?=\\S+$).{8,20}$";
    private static final Pattern pattern = Pattern.compile(REGEX);

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(password)) {
            return false;
        }

        return pattern.matcher(password).matches();
    }
}
