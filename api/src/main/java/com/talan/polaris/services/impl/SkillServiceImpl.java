package com.talan.polaris.services.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talan.polaris.entities.SkillEntity;
import com.talan.polaris.enumerations.SkillTypeEnum;
import com.talan.polaris.repositories.SkillRepository;
import com.talan.polaris.services.SkillService;

/**
 * An implementation of {@link SkillService}, containing business methods
 * implementations specific to {@link SkillEntity}, and may override some
 * of the common methods' implementations inherited from
 * {@link GenericServiceImpl}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Service
public class SkillServiceImpl
        extends GenericServiceImpl<SkillEntity>
        implements SkillService {

    private final SkillRepository skillRepository;

    @Autowired
    public SkillServiceImpl(SkillRepository repository) {
        super(repository);
        this.skillRepository = repository;
    }

    @Override
    public Collection<SkillEntity> findSkillsBySkillType(SkillTypeEnum skillType) {
        return this.skillRepository.findSkillsBySkillType(skillType);
    }
   
}
