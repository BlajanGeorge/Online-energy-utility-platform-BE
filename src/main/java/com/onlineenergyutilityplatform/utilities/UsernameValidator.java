package com.onlineenergyutilityplatform.utilities;


import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {
    private static final String REGEX = "^(?=.{8,20}$)[a-zA-Z0-9._]+";
    private static final Pattern pattern = Pattern.compile(REGEX);

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(username)) {
            return false;
        }

        return pattern.matcher(username).matches();
    }
}
