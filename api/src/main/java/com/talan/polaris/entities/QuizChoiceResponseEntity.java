package com.talan.polaris.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.talan.polaris.enumerations.ResponseTypeEnum;

import static com.talan.polaris.enumerations.ResponseTypeEnum.ResponseTypeConstants.QUIZ_CHOICE_RESPONSE_TYPE;

/**
 * A mapped entity representing a quiz choice response domain model, 
 * a subtype of the choice response domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@DiscriminatorValue(QUIZ_CHOICE_RESPONSE_TYPE)
public class QuizChoiceResponseEntity extends ChoiceResponseEntity {

    private static final long serialVersionUID = 3771793083515675248L;

    public QuizChoiceResponseEntity() {
        super();
    }

    public QuizChoiceResponseEntity(
            QuestionEntity question,
            CollaboratorEntity collaborator,
            EvaluationEntity evaluation) {

        super(ResponseTypeEnum.QUIZ_CHOICE, question, collaborator, evaluation);

    }

}
