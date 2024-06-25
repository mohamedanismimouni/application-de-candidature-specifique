package com.talan.polaris.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.talan.polaris.enumerations.ResponseTypeEnum;

import static com.talan.polaris.enumerations.ResponseTypeEnum.ResponseTypeConstants.REGULAR_CHOICE_RESPONSE_TYPE;

/**
 * A mapped entity representing a regular choice response domain model, 
 * a subtype of the choice response domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@DiscriminatorValue(REGULAR_CHOICE_RESPONSE_TYPE)
public class RegularChoiceResponseEntity extends ChoiceResponseEntity {

    private static final long serialVersionUID = -1463928249476262622L;

    public RegularChoiceResponseEntity() {
        super();
    }

    public RegularChoiceResponseEntity(
            QuestionEntity question,
            CollaboratorEntity collaborator,
            EvaluationEntity evaluation) {

        super(ResponseTypeEnum.REGULAR_CHOICE, question, collaborator, evaluation);

    }

}
