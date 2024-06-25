package com.talan.polaris.utils.validation.validators;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.talan.polaris.dto.PasswordSubmission;
import com.talan.polaris.utils.validation.constraints.PasswordConfirmationConstraint;

/**
 * PasswordConfirmationValidator.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public class PasswordConfirmationValidator
        implements ConstraintValidator<PasswordConfirmationConstraint, PasswordSubmission> {

    @Override
    public boolean isValid(PasswordSubmission value, ConstraintValidatorContext context) {
        return (value == null) ||
                Objects.equals(value.getPassword(), value.getPasswordConfirmation());
    }

}
