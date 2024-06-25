package com.talan.polaris.services;

import java.util.Collection;

import com.talan.polaris.entities.SkillEntity;
import com.talan.polaris.enumerations.SkillTypeEnum;

/**
 * A specific extension of {@link GenericService} containing business methods
 * definitions related to {@link SkillEntity}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public interface SkillService extends GenericService<SkillEntity> {

    /**
     * Finds skills having the given {@code skillType}.
     * 
     * @param skillType of each returned skill.
     * 
     * @return the found skills if any.
     */
    public Collection<SkillEntity> findSkillsBySkillType(SkillTypeEnum skillType);

}
