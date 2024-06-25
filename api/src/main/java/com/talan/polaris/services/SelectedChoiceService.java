package com.talan.polaris.services;

import java.util.Collection;

import com.talan.polaris.entities.ChoiceEntity;
import com.talan.polaris.entities.SelectedChoiceEntity;

/**
 * A specific extension of {@link GenericService} containing business methods
 * definitions related to {@link SelectedChoiceEntity}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public interface SelectedChoiceService extends GenericService<SelectedChoiceEntity> {

    /**
     * Finds choices associated with a response given by its {@code responseId}.
     * 
     * @param responseId
     * 
     * @return the choices matching the given parameter if any.
     */
    public Collection<ChoiceEntity> findChoicesByResponseId(String responseId);

}
