package com.talan.polaris.services;

import java.util.Collection;

import com.talan.polaris.entities.CareerPathEntity;

/**
 * A specific extension of {@link GenericService} containing business methods
 * definitions related to {@link CareerPathEntity}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public interface CareerPathService extends GenericService<CareerPathEntity> {

    /**
     * Finds career paths leading to a
     * {@link com.talan.polaris.entities.CareerStepEntity} given by its id.
     * 
     * @param toCareerStepID id of the 
     * {@link com.talan.polaris.entities.CareerStepEntity} included in 
     * {@link CareerPathEntity} as {@literal toCareerStep}.
     * 
     * @return the found career paths if any.
     */
    public Collection<CareerPathEntity> findInboundCareerPaths(String toCareerStepID);

    /**
     * Finds career paths leaving a
     * {@link com.talan.polaris.entities.CareerStepEntity} given by its id.
     * 
     * @param toCareerStepID id of the 
     * {@link com.talan.polaris.entities.CareerStepEntity} included in 
     * {@link CareerPathEntity} as {@literal fromCareerStep}.
     * 
     * @return the found career paths if any.
     */
    public Collection<CareerPathEntity> findOutboundCareerPaths(String fromCareerStepID);

    /**
     * Creates a career path.
     * <p>
     * If the {@link com.talan.polaris.entities.CareerStepEntity} included in
     * the careerPathEntity parameter as {@literal fromCareerStep} is not yet 
     * created, it will be. The career paths leading to the 
     * {@link com.talan.polaris.entities.CareerStepEntity} included in the 
     * careerPathEntity parameter as {@literal toCareerStep} will be moved to
     * the newly created {@link com.talan.polaris.entities.CareerStepEntity},
     * and finally the new {@link CareerPathEntity} will be created.
     * <p>
     * If the {@link com.talan.polaris.entities.CareerStepEntity} included in
     * the careerPathEntity parameter as {@literal toCareerStep} is not yet
     * created, it will be. And the new {@link CareerPathEntity} will be 
     * created.
     * <p>
     * If the two {@link com.talan.polaris.entities.CareerStepEntity} included
     * in the careerPathEntity parameter as {@literal fromCareerStep} and 
     * {@literal toCareerStep} already both exist, then only the new 
     * {@link CareerPathEntity} will be created.
     * 
     * @param careerPathEntity the career path to be created between the two
     * specified {@link com.talan.polaris.entities.CareerStepEntity}.
     * 
     * @return the created career path.
     * 
     * @throws IllegalArgumentException if the two 
     * {@link com.talan.polaris.entities.CareerStepEntity} included in the 
     * careerPathEntity parameter as {@literal fromCareerStep} and 
     * {@literal toCareerStep} do not exist.
     */
    public CareerPathEntity createCareerPath(CareerPathEntity careerPathEntity);

}
