package com.talan.polaris.utils.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.talan.polaris.utils.validation.validators.QuestionChoiceValidator;

import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_VALIDATION_CONSTRAINTS_VIOLATION_QUESTION_CHOICE_CONSTRAINT;

/**
 * QuestionChoiceConstraint.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Documented
@Constraint(validatedBy = QuestionChoiceValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface QuestionChoiceConstraint {

    String message() default "{" + ERROR_BAD_REQUEST_VALIDATION_CONSTRAINTS_VIOLATION_QUESTION_CHOICE_CONSTRAINT + "}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
