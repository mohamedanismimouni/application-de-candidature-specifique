package com.talan.polaris.utils.validation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.talan.polaris.entities.QuestionEntity;
import com.talan.polaris.enumerations.QuestionTypeEnum;
import com.talan.polaris.enumerations.SurveyTypeEnum;
import com.talan.polaris.utils.validation.constraints.SurveyQuestionConstraint;

/**
 * SurveyQuestionValidator.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public class SurveyQuestionValidator
        implements ConstraintValidator<SurveyQuestionConstraint, QuestionEntity> {

    @Override
    public boolean isValid(QuestionEntity value, ConstraintValidatorContext context) {

        return (value == null) ||
                (value.getSurveyType() == null) ||
                (value.getQuestionType() == null) ||
                (value.getSurveyType().equals(SurveyTypeEnum.EXPLORATION) && 
                        value.getQuestionType().equals(QuestionTypeEnum.QUIZ_CHOICE)) ||
                (!value.getSurveyType().equals(SurveyTypeEnum.EXPLORATION) && 
                        !value.getQuestionType().equals(QuestionTypeEnum.QUIZ_CHOICE));

    }

}
