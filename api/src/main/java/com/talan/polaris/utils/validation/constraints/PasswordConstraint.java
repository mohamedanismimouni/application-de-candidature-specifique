package com.talan.polaris.utils.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.talan.polaris.utils.validation.validators.PasswordValidator;

import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_VALIDATION_CONSTRAINTS_VIOLATION_PASSWORD_CONSTRAINT;

/**
 * PasswordConstraint.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {

    String message() default "{" + ERROR_BAD_REQUEST_VALIDATION_CONSTRAINTS_VIOLATION_PASSWORD_CONSTRAINT + "}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
