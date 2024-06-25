package com.talan.polaris.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talan.polaris.entities.ProfileEntity;
import com.talan.polaris.entities.RequiredSkillEntity;
import com.talan.polaris.enumerations.SkillTypeEnum;
import com.talan.polaris.repositories.RequiredSkillRepository;
import com.talan.polaris.services.RequiredSkillService;

/**
 * An implementation of {@link RequiredSkillService}, containing business
 * methods implementations specific to {@link RequiredSkillEntity}, and may
 * override some of the common methods' implementations inherited from
 * {@link GenericServiceImpl}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Service
public class RequiredSkillServiceImpl
        extends GenericServiceImpl<RequiredSkillEntity>
        implements RequiredSkillService {

    private final RequiredSkillRepository requiredSkillRepository;

    @Autowired
    public RequiredSkillServiceImpl(RequiredSkillRepository repository) {
        super(repository);
        this.requiredSkillRepository = repository;
    }

    @Override
    public Collection<RequiredSkillEntity> findRequiredSkillsByProfileIdAndSkillType(
            String profileId, 
            SkillTypeEnum skillType) {

        List<RequiredSkillEntity> requiredSkills = new ArrayList<>(
                this.requiredSkillRepository.findRequiredSkillsByProfileIdAndSkillType(profileId, skillType));

        Collections.sort(requiredSkills);

        return requiredSkills;

    }

    @Override
    public Collection<ProfileEntity> findInitializedProfilesByTeamId(String teamId) {
        return this.requiredSkillRepository.findInitializedProfilesByTeamId(teamId);
    }

}
