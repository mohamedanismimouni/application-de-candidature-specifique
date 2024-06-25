package com.talan.polaris.entities;

import java.time.Instant;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.talan.polaris.enumerations.ChoiceTypeEnum;

import static com.talan.polaris.enumerations.ChoiceTypeEnum.ChoiceTypeConstants.REGULAR_CHOICE_TYPE;

/**
 * A mapped entity representing a regular choice domain model, 
 * a subtype of the choice domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@DiscriminatorValue(REGULAR_CHOICE_TYPE)
public class RegularChoiceEntity extends ChoiceEntity {

    private static final long serialVersionUID = 1317458852295164924L;

    public RegularChoiceEntity() {
        super();
    }

    public RegularChoiceEntity(
            String id,
            Instant createdAt,
            Instant updatedAt,
            int position,
            String content,
            boolean enabled,
            ChoiceQuestionEntity question) {

        super(id, createdAt, updatedAt, ChoiceTypeEnum.REGULAR, position, content, enabled, question);

    }

}
