package com.talan.polaris.services.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talan.polaris.entities.CareerStepEntity;
import com.talan.polaris.entities.ProfileEntity;
import com.talan.polaris.repositories.ProfileRepository;
import com.talan.polaris.services.CareerPathService;
import com.talan.polaris.services.ProfileService;
import com.talan.polaris.services.RequiredSkillService;

import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_PROFILES_CHAINING;

/**
 * An implementation of {@link ProfileService}, containing business methods
 * implementations specific to {@link ProfileEntity}, and may override some of
 * the common methods' implementations inherited from
 * {@link GenericServiceImpl}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Service
public class ProfileServiceImpl
        extends GenericServiceImpl<ProfileEntity>
        implements ProfileService {

    private final ProfileRepository profileRepository;
    private final RequiredSkillService requiredSkillService;
    private final CareerPathService careerPathService;

    @Autowired
    public ProfileServiceImpl(
            ProfileRepository repository,
            RequiredSkillService requiredSkillService,
            CareerPathService careerPathService) {

        super(repository);
        this.profileRepository = repository;
        this.requiredSkillService = requiredSkillService;
        this.careerPathService = careerPathService;

    }

    @Override
    public Collection<ProfileEntity> findProfilesByTeamId(
            String teamId,
            boolean initializedOnly) {

        if (initializedOnly) {
            return this.requiredSkillService.findInitializedProfilesByTeamId(teamId);
        } else {
            return this.profileRepository.findProfilesByTeamId(teamId);
        }

    }

    @Override
    public Collection<ProfileEntity> findProfilesByTeamIdAndCareerStepId(
            String teamId,
            String careerStepId) {

        return this.profileRepository.findProfilesByTeamIdAndCareerStepId(teamId, careerStepId);

    }

    @Override
    public Collection<CareerStepEntity> findCareerStepsAssociatedToProfilesWithTeamId(
            String teamId) {
    
        return this.profileRepository.findCareerStepsAssociatedToProfilesWithTeamId(teamId);

    }

    @Override
    public ProfileEntity createProfile(ProfileEntity profile) {

        List<String> predecessorCareerStepsIds = this.careerPathService.findInboundCareerPaths(
                profile.getCareerStep().getId())
                        .stream()
                        .map(careerPath -> careerPath.getFromCareerStep().getId())
                        .collect(Collectors.toList());

        if (!predecessorCareerStepsIds.isEmpty() && 
                this.profileRepository.countProfiles(
                        profile.getTeam().getId(),
                        predecessorCareerStepsIds) == 0) {

            throw new IllegalArgumentException(ERROR_BAD_REQUEST_PROFILES_CHAINING);

        }

        return this.create(profile);

    }

    @Override
    public ProfileEntity findProfileByLabel(String profilLabel) {
        return this.profileRepository.findProfileByName(profilLabel);
    }

}
