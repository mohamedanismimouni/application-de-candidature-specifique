package com.talan.polaris.entities;

import javax.persistence.Entity;

import com.talan.polaris.enumerations.ResponseTypeEnum;

/**
 * A mapped entity representing a super model for all different close ended
 * responses domain models, a subtype of the response domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
public class CloseEndedResponseEntity extends ResponseEntity {

    private static final long serialVersionUID = 8767990826746954729L;

    public CloseEndedResponseEntity() {
        super();
    }

    public CloseEndedResponseEntity(
            ResponseTypeEnum responseType,
            QuestionEntity question,
            CollaboratorEntity collaborator,
            EvaluationEntity evaluation) {

        super(responseType, question, collaborator, evaluation);

    }

}
