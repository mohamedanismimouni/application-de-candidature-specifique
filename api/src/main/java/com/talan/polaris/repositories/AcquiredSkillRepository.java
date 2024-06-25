package com.talan.polaris.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.talan.polaris.entities.AcquiredSkillEntity;

/**
 * A specific extension of {@link GenericRepository} that may contain additional
 * data access operations tied to the {@link AcquiredSkillEntity} domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Repository
public interface AcquiredSkillRepository extends GenericRepository<AcquiredSkillEntity> {

    @Query("SELECT acquiredSkill FROM AcquiredSkillEntity AS acquiredSkill "
            + "WHERE acquiredSkill.collaborator.id = :collaboratorId")
    public Collection<AcquiredSkillEntity> findAcquiredSkillsByCollaboratorId(
            @Param("collaboratorId") Long collaboratorId);

    @Query("SELECT acquiredSkill FROM AcquiredSkillEntity AS acquiredSkill "
            + "WHERE acquiredSkill.collaborator.id IN :collaboratorsIds")
    public Collection<AcquiredSkillEntity> findAcquiredSkillsByCollaboratorsIds(
            @Param("collaboratorsIds") Collection<Long> collaboratorsIds);

}
