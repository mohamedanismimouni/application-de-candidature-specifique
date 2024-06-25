package com.talan.polaris.services;

import java.util.Collection;

import com.talan.polaris.entities.ProfileEntity;
import com.talan.polaris.entities.RequiredSkillEntity;
import com.talan.polaris.enumerations.SkillTypeEnum;

/**
 * A specific extension of {@link GenericService} containing business methods
 * definitions related to {@link RequiredSkillEntity}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public interface RequiredSkillService extends GenericService<RequiredSkillEntity> {

    /**
     * Finds required skills having the given {@code profileId} and
     * {@code skillType} if specified.
     * 
     * @param profileId
     * @param skillType optional
     * 
     * @return the required skills matching the given parameters if any, ordered 
     * by skill weight.
     */
    public Collection<RequiredSkillEntity> findRequiredSkillsByProfileIdAndSkillType(
            String profileId,
            SkillTypeEnum skillType);

    /**
     * Finds initialized profiles (associated to required skills) having 
     * the given {@code teamId}.
     * 
     * @param teamId
     * 
     * @return the found profiles if any.
     */
    public Collection<ProfileEntity> findInitializedProfilesByTeamId(String teamId);

}
