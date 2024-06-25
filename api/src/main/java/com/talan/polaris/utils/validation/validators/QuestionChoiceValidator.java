package com.talan.polaris.utils.validation.validators;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.talan.polaris.entities.ChoiceEntity;
import com.talan.polaris.entities.ChoiceQuestionEntity;
import com.talan.polaris.enumerations.ChoiceTypeEnum;
import com.talan.polaris.enumerations.QuestionTypeEnum;
import com.talan.polaris.utils.validation.constraints.QuestionChoiceConstraint;

/**
 * QuestionChoiceValidator.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public class QuestionChoiceValidator
        implements ConstraintValidator<QuestionChoiceConstraint, ChoiceQuestionEntity> {

    @Override
    public boolean isValid(ChoiceQuestionEntity value, ConstraintValidatorContext context) {

        if ((value == null) || (value.getChoices() == null))
            return true;

        ChoiceTypeEnum choiceType = null;

        if (Objects.equals(QuestionTypeEnum.REGULAR_CHOICE, value.getQuestionType())) {
            choiceType = ChoiceTypeEnum.REGULAR;
        } else if (Objects.equals(QuestionTypeEnum.QUIZ_CHOICE, value.getQuestionType())) {
            choiceType = ChoiceTypeEnum.QUIZ;
        }

        for (ChoiceEntity choice : value.getChoices()) {
            if (!Objects.equals(choiceType, choice.getChoiceType())) {
                return false;
            }
        }

        return true;

    }

}
