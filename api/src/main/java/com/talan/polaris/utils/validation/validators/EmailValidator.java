package com.talan.polaris.utils.validation.validators;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.talan.polaris.utils.validation.constraints.EmailConstraint;

import static com.talan.polaris.constants.CommonConstants.PATTERN_EMAIL;

/**
 * EmailValidator.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (value == null) ||
                Pattern.compile(PATTERN_EMAIL).matcher(value).matches();
    }

}
