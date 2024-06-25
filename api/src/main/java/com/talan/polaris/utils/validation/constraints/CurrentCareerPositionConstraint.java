package com.talan.polaris.utils.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.talan.polaris.utils.validation.validators.CurrentCareerPositionValidator;

import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_VALIDATION_CONSTRAINTS_VIOLATION_CURRENT_CAREER_POSITION_CONSTRAINT;

/**
 * CurrentCareerPositionConstraint.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Documented
@Constraint(validatedBy = CurrentCareerPositionValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentCareerPositionConstraint {

    String message() default "{" + ERROR_BAD_REQUEST_VALIDATION_CONSTRAINTS_VIOLATION_CURRENT_CAREER_POSITION_CONSTRAINT + "}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
