package com.talan.polaris.utils.validation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.talan.polaris.entities.CareerPositionEntity;
import com.talan.polaris.enumerations.CareerPositionStatusEnum;
import com.talan.polaris.utils.validation.constraints.CurrentCareerPositionConstraint;

/**
 * CurrentCareerPositionValidator.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public class CurrentCareerPositionValidator
        implements ConstraintValidator<CurrentCareerPositionConstraint, CareerPositionEntity> {

    @Override
    public boolean isValid(CareerPositionEntity value, ConstraintValidatorContext context) {

        if ((value == null) || !(CareerPositionStatusEnum.CURRENT.equals(value.getStatus())))
            return true;

        return value.getSupervisor() != null && value.getStartedAt() != null;

    }

}
