package com.talan.polaris.services;

import java.util.Collection;

import com.talan.polaris.entities.CareerStepEntity;

/**
 * A specific extension of {@link GenericService} containing business methods
 * definitions related to {@link CareerStepEntity}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public interface CareerStepService extends GenericService<CareerStepEntity> {

    /**
     * Finds career steps associated to profiles having the given {@code teamId}.
     * 
     * @param teamId
     * 
     * @return the found career steps if any.
     */
    public Collection<CareerStepEntity> findCareerStepsAssociatedToProfilesWithTeamId(
            String teamId);

    /**
     * Attempts to delete a {@link CareerStepEntity} given by its id.
     * <p>
     * Any inbound or outbound 
     * {@link com.talan.polaris.entities.CareerPathEntity} will be deleted.
     * <p>
     * Predecessor and sucessor {@link CareerStepEntity}s will be connected
     * by new {@link com.talan.polaris.entities.CareerPathEntity}s.
     * 
     * @param careerStepId of the {@link CareerStepEntity} to be deleted.
     */
    public void deleteCareerStepById(String careerStepId);

}
