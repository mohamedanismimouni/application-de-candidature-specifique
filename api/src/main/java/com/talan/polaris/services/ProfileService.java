package com.talan.polaris.services;

import java.util.Collection;

import com.talan.polaris.entities.CareerStepEntity;
import com.talan.polaris.entities.ProfileEntity;

/**
 * A specific extension of {@link GenericService} containing business methods
 * definitions related to {@link ProfileEntity}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public interface ProfileService extends GenericService<ProfileEntity> {

    /**
     * Finds profiles having the given {@code teamId}.
     * <p>
     * If {@code initializedOnly} is set to {@code true}, only the 
     * profiles associated to required skills will be returned.
     * 
     * @param teamId
     * @param initializedOnly
     * 
     * @return the profiles matching the given parameter if any.
     */
    public Collection<ProfileEntity> findProfilesByTeamId(
            String teamId,
            boolean initializedOnly);

    /**
     * Finds profiles having the given {@code teamId} and {@careerStepId}.
     * 
     * @param teamId
     * @param careerStepId
     * 
     * @return the profiles matching the given parameters if any.
     */
    public Collection<ProfileEntity> findProfilesByTeamIdAndCareerStepId(
            String teamId,
            String careerStepId);

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
     * Creates the given {@code profile}.
     * 
     * @param profile to be created.
     * 
     * @return the created profile.
     * 
     * @throws IllegalArgumentException if the 
     * {@link com.talan.polaris.entities.CareerStepEntity} associated with the 
     * given {@code profile} has predecessor career steps and none of them has 
     * a created {@link ProfileEntity} with the same 
     * {@link com.talan.polaris.entities.TeamEntity} as the one associated with 
     * the given {@code profile}.
     */
    public ProfileEntity createProfile(ProfileEntity profile);

    /**
     * Finds profile having the given {@code profilLabel}.
     *
     * @param profilLabel
     *
     * @return the profiles matching the given parameters if any.
     */
    public ProfileEntity findProfileByLabel(String profilLabel);
}
