package com.talan.polaris.services.impl;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talan.polaris.entities.AcquiredSkillEntity;
import com.talan.polaris.entities.AcquiredSkillLevelEntity;
import com.talan.polaris.repositories.AcquiredSkillRepository;
import com.talan.polaris.services.AcquiredSkillService;

/**
 * An implementation of {@link AcquiredSkillService}, containing business
 * methods implementations specific to {@link AcquiredSkillEntity}, and may
 * override some of the common methods' implementations inherited from
 * {@link GenericServiceImpl}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Service
public class AcquiredSkillServiceImpl
        extends GenericServiceImpl<AcquiredSkillEntity>
        implements AcquiredSkillService {

    private final AcquiredSkillRepository acquiredSkillRepository;

    @Autowired
    public AcquiredSkillServiceImpl(AcquiredSkillRepository repository) {
        super(repository);
        this.acquiredSkillRepository = repository;
    }

    @Override
    public Collection<AcquiredSkillEntity> findAcquiredSkillsByCollaboratorId(
            Long collaboratorId) {

        return this.acquiredSkillRepository.findAcquiredSkillsByCollaboratorId(
                collaboratorId);

    }

    @Override
    public Collection<AcquiredSkillEntity> findAcquiredSkillsByCollaboratorsIds(
            Collection<Long> collaboratorsIds) {

        return this.acquiredSkillRepository.findAcquiredSkillsByCollaboratorsIds(
                collaboratorsIds);

    }

    @Override
    @Transactional
    public Collection<AcquiredSkillEntity> createAcquiredSkills(
            Collection<AcquiredSkillEntity> acquiredSkills) {

        acquiredSkills.forEach((AcquiredSkillEntity acquiredSkill) -> {
            acquiredSkill.getProgress().forEach((AcquiredSkillLevelEntity acquiredSkillLevel) -> {
                acquiredSkillLevel.setId(UUID.randomUUID().toString());
                acquiredSkillLevel.setAcquiredSkill(acquiredSkill);
            });
            create(acquiredSkill);
        });

        return acquiredSkills;

    }

    @Override
    @Transactional
    public AcquiredSkillLevelEntity createAcquiredSkillLevel(
            String acquiredSkillId,
            AcquiredSkillLevelEntity acquiredSkillLevel) {

        AcquiredSkillEntity acquiredSkill = findById(acquiredSkillId);

        acquiredSkillLevel.setId(UUID.randomUUID().toString());
        acquiredSkillLevel.setAcquiredSkill(acquiredSkill);

        acquiredSkill.getProgress().add(acquiredSkillLevel);

        return acquiredSkillLevel;

    }

}
