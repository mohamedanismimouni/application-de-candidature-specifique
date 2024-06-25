package com.talan.polaris.utils.validation.validators;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.talan.polaris.utils.validation.constraints.PasswordConstraint;

import static com.talan.polaris.constants.CommonConstants.PATTERN_PASSWORD;

/**
 * PasswordValidator.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (value == null) ||
                Pattern.compile(PATTERN_PASSWORD).matcher(value).matches();
    }

}
