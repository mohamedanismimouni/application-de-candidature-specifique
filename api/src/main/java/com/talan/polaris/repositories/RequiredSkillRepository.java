package com.talan.polaris.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.talan.polaris.entities.ProfileEntity;
import com.talan.polaris.entities.RequiredSkillEntity;
import com.talan.polaris.enumerations.SkillTypeEnum;

/**
 * A specific extension of {@link GenericRepository} that may contain additional
 * data access operations tied to the {@link RequiredSkillEntity} domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Repository
public interface RequiredSkillRepository extends GenericRepository<RequiredSkillEntity> {

    @Query("SELECT requiredSkill FROM RequiredSkillEntity AS requiredSkill "
            + "WHERE requiredSkill.profile.id LIKE :profileId "
            + "AND (:skillType IS NULL OR requiredSkill.skill.skillType LIKE :skillType)")
    public Collection<RequiredSkillEntity> findRequiredSkillsByProfileIdAndSkillType(
            @Param("profileId") String profileId,
            @Param("skillType") SkillTypeEnum skillType);

    @Query("SELECT DISTINCT requiredSkill.profile FROM RequiredSkillEntity AS requiredSkill "
            + "WHERE requiredSkill.profile.team.id LIKE :teamId "
            + "ORDER BY requiredSkill.profile.label ASC")
    public Collection<ProfileEntity> findInitializedProfilesByTeamId(
            @Param("teamId") String teamId);

}
