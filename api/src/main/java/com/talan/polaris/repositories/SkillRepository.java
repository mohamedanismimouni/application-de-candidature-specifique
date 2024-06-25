package com.talan.polaris.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.talan.polaris.entities.SkillEntity;
import com.talan.polaris.enumerations.SkillTypeEnum;

/**
 * A specific extension of {@link GenericRepository} that may contain additional
 * data access operations tied to the {@link SkillEntity} domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Repository
public interface SkillRepository extends GenericRepository<SkillEntity> {

    @Query("SELECT skill FROM SkillEntity AS skill "
            + "WHERE skill.skillType LIKE :skillType "
            + "ORDER BY skill.label ASC")
    public Collection<SkillEntity> findSkillsBySkillType(
            @Param("skillType") SkillTypeEnum skillType);

}
