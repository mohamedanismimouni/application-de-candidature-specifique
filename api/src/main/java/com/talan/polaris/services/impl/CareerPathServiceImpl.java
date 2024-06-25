package com.talan.polaris.services.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talan.polaris.entities.CareerPathEntity;
import com.talan.polaris.repositories.CareerPathRepository;
import com.talan.polaris.services.CareerPathService;
import com.talan.polaris.services.CareerStepService;

import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_CAREER_PATH_CREATION;

/**
 * An implementation of {@link CareerPathService}, containing business methods
 * implementations specific to {@link CareerPathEntity}, and may override some
 * of the common methods' implementations inherited from
 * {@link GenericServiceImpl}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Service
public class CareerPathServiceImpl
        extends GenericServiceImpl<CareerPathEntity>
        implements CareerPathService {

    private final CareerPathRepository careerPathRepository;
    private final CareerStepService careerStepService;

    @Autowired
    public CareerPathServiceImpl(
            CareerPathRepository repository,
            CareerStepService careerStepService) {

        super(repository);
        this.careerPathRepository = repository;
        this.careerStepService = careerStepService;

    }

    @Override
    public Collection<CareerPathEntity> findInboundCareerPaths(String toCareerStepID) {
        return this.careerPathRepository.findInboundCareerPaths(toCareerStepID);
    }

    @Override
    public Collection<CareerPathEntity> findOutboundCareerPaths(String fromCareerStepID) {
        return this.careerPathRepository.findOutboundCareerPaths(fromCareerStepID);
    }

    @Override
    @Transactional
    public CareerPathEntity createCareerPath(CareerPathEntity careerPathEntity) {

        CareerPathEntity createdCareerPathEntity;

        if (careerPathEntity.getFromCareerStep().getId() == null && 
                careerPathEntity.getToCareerStep().getId() == null) {

            throw new IllegalArgumentException(ERROR_BAD_REQUEST_CAREER_PATH_CREATION);

        } else if (careerPathEntity.getFromCareerStep().getId() == null &&
                careerPathEntity.getToCareerStep().getId() != null) {
            
            this.careerStepService.findById(careerPathEntity.getToCareerStep().getId());

            this.careerStepService.create(careerPathEntity.getFromCareerStep());

            createdCareerPathEntity = create(careerPathEntity);

            this.careerPathRepository.moveInboundCareerPaths(
                    careerPathEntity.getFromCareerStep().getId(),
                    careerPathEntity.getToCareerStep().getId());

        } else if (careerPathEntity.getFromCareerStep().getId() != null &&
                careerPathEntity.getToCareerStep().getId() == null) {

            this.careerStepService.findById(careerPathEntity.getFromCareerStep().getId());

            this.careerStepService.create(careerPathEntity.getToCareerStep());

            createdCareerPathEntity = create(careerPathEntity);

        } else {

            this.careerStepService.findById(careerPathEntity.getFromCareerStep().getId());
            this.careerStepService.findById(careerPathEntity.getToCareerStep().getId());

            createdCareerPathEntity = create(careerPathEntity);

        }

        return createdCareerPathEntity;

    }

}
