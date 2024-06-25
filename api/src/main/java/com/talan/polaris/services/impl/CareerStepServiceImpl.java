package com.talan.polaris.services.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talan.polaris.entities.CareerPathEntity;
import com.talan.polaris.entities.CareerStepEntity;
import com.talan.polaris.repositories.CareerStepRepository;
import com.talan.polaris.services.CareerPathService;
import com.talan.polaris.services.CareerStepService;
import com.talan.polaris.services.ProfileService;

/**
 * An implementation of {@link CareerStepService}, containing business methods
 * implementations specific to {@link CareerStepEntity}, and may override some
 * of the common methods' implementations inherited from
 * {@link GenericServiceImpl}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Service
public class CareerStepServiceImpl
        extends GenericServiceImpl<CareerStepEntity>
        implements CareerStepService {

    private final CareerPathService careerPathService;
    private final ProfileService profileService;

    @Autowired
    public CareerStepServiceImpl(
            CareerStepRepository repository,
            @Lazy CareerPathService careerPathService,
            @Lazy ProfileService profileService) {

        super(repository);
        this.careerPathService = careerPathService;
        this.profileService = profileService;

    }

    @Override
    public Collection<CareerStepEntity> findCareerStepsAssociatedToProfilesWithTeamId(
            String teamId) {

        return this.profileService.findCareerStepsAssociatedToProfilesWithTeamId(teamId);

    }

    @Override
    @Transactional
    public void deleteCareerStepById(String careerStepId) {

        Collection<CareerPathEntity> inboundCareerPaths = this.careerPathService.findInboundCareerPaths(careerStepId);
        Collection<CareerPathEntity> outboundCareerPaths = this.careerPathService.findOutboundCareerPaths(careerStepId);

        inboundCareerPaths.forEach(inboundCareerPath -> 
            outboundCareerPaths.forEach(outboundCareerPath -> {
                CareerPathEntity careerPath = new CareerPathEntity(
                    inboundCareerPath.getYearsOfExperience() + outboundCareerPath.getYearsOfExperience(),
                    inboundCareerPath.getFromCareerStep(),
                    outboundCareerPath.getToCareerStep()
                );
                try {
                    this.careerPathService.create(careerPath);
                } catch (DataIntegrityViolationException e) {
                    // such a path might already exist
                }
            })
        );

        Collection<CareerPathEntity> obsoleteCareerPaths = new ArrayList<>();
        obsoleteCareerPaths.addAll(inboundCareerPaths);
        obsoleteCareerPaths.addAll(outboundCareerPaths);

        this.careerPathService.deleteInBatch(obsoleteCareerPaths);

        deleteById(careerStepId);

    }

}
