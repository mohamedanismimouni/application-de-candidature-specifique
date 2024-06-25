package com.talan.polaris.utils.validation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.talan.polaris.entities.RatingScaleQuestionEntity;
import com.talan.polaris.utils.validation.constraints.ScaleConstraint;

/**
 * ScaleValidator.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public class ScaleValidator
        implements ConstraintValidator<ScaleConstraint, RatingScaleQuestionEntity> {

    @Override
    public boolean isValid(RatingScaleQuestionEntity value, ConstraintValidatorContext context) {
        return (value == null) || (value.getHighestValue() > value.getLowestValue());
    }

}
