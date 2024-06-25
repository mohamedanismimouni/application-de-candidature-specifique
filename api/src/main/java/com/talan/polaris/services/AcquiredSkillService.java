package com.talan.polaris.services;

import java.util.Collection;

import com.talan.polaris.entities.AcquiredSkillEntity;
import com.talan.polaris.entities.AcquiredSkillLevelEntity;

/**
 * A specific extension of {@link GenericService} containing business methods
 * definitions related to {@link AcquiredSkillEntity}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public interface AcquiredSkillService extends GenericService<AcquiredSkillEntity> {

    /**
     * Finds acquired skills having the given {@code collaboratorId}.
     * 
     * @param collaboratorId
     * 
     * @return the acquired skills matching the given parameter if any.
     */
    public Collection<AcquiredSkillEntity> findAcquiredSkillsByCollaboratorId(
            Long collaboratorId);

    /**
     * Finds acquired skills having one of the given {@code collaboratorsIds}.
     * 
     * @param collaboratorsIds
     * 
     * @return the acquired skills matching the given parameter if any.
     */
    public Collection<AcquiredSkillEntity> findAcquiredSkillsByCollaboratorsIds(
            Collection<Long> collaboratorsIds);

    /**
     * Creates the given {@code acquiredSkills} and their first
     * {@link AcquiredSkillLevelEntity}.
     * 
     * @param acquiredSkills
     * 
     * @return the created acquired skills.
     */
    public Collection<AcquiredSkillEntity> createAcquiredSkills(
            Collection<AcquiredSkillEntity> acquiredSkills);

    /**
     * Adds an acquired skill level to an {@link AcquiredSkillEntity} given by its
     * {@code acquiredSkillId}
     * 
     * @param acquiredSkillId
     * @param acquiredSkillLevel
     * 
     * @return the created acquired skill level.
     */
    public AcquiredSkillLevelEntity createAcquiredSkillLevel(
            String acquiredSkillId,
            AcquiredSkillLevelEntity acquiredSkillLevel);

}
