package com.talan.polaris.entities;

import javax.persistence.Entity;

import com.talan.polaris.enumerations.ResponseTypeEnum;

/**
 * A mapped entity representing a super model for all different choice 
 * responses domain models, a subtype of the close ended response 
 * domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
public class ChoiceResponseEntity extends CloseEndedResponseEntity {

    private static final long serialVersionUID = -1249386347721701178L;

    public ChoiceResponseEntity() {
        super();
    }

    public ChoiceResponseEntity(
            ResponseTypeEnum responseType,
            QuestionEntity question,
            CollaboratorEntity collaborator,
            EvaluationEntity evaluation) {

        super(responseType, question, collaborator, evaluation);

    }

}
