package com.talan.polaris.services;

import java.util.Collection;

import javax.json.JsonPatch;

import com.talan.polaris.entities.CareerPositionEntity;
import com.talan.polaris.enumerations.CareerPositionStatusEnum;

/**
 * A specific extension of {@link GenericService} containing business methods
 * definitions related to {@link CareerPositionEntity}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public interface CareerPositionService  {

    /**
     * Finds career positions associated to collaborators with the given
     * {@code collaboratorId} and having the given {@code status} if specified.
     * 
     * @param collaboratorId
     * @param status optional
     * 
     * @return the career positions matching the given parameters if any.
     */
    public Collection<CareerPositionEntity> findCareerPositionsByCollaboratorIdAndStatus(
            Long collaboratorId,
            CareerPositionStatusEnum status);

    /**
     * Creates a career position, and eventually creates a mentorship for each 
     * required skill of the profile specified in the {@code careerPosition}.
     * <p>
     * Mentors are chosen randomly from a list of initialized team members having
     * acquired skill levels higher than the required ones. If there are no 
     * available mentors, the team manager will be chosen as one.
     * <p>
     * If the career position to be created has {@code NEXT} status, then if there 
     * is an old career position with the same status, it will be deleted with all 
     * its associated mentorships.
     * 
     * @param careerPosition
     * 
     * @return the created career position.
     * 
     * @throws IllegalArgumentException if the id of the collaborator specified
     * in {@code careerPosition} does correspond to an existing user but it is 
     * not a collaborator, or if the profile specified in {@code careerPosition}
     * is not one of the initialized team profiles, or if there is an attempt to
     * create a {@code CURRENT} career position and the collaborator already has 
     * one, or if there is an attempt to create a {@code NEXT} career position 
     * and there is no {@code CURRENT} one, or if there is already a {@code NEXT} 
     * career position with the same specified profile, or if there is no career 
     * path linking the career steps of the {@code CURRENT} and {@code NEXT} 
     * career positions' profiles, or if the specified supervisor is not an 
     * initialized more experienced team member as the collaborator or he is not 
     * the team manager, and finally if the career position starting date is before 
     * the collaborator recruitment date.
     */
    public CareerPositionEntity createCareerPosition(CareerPositionEntity careerPosition);

    /**
     * Partially updates a {@link CareerPositionEntity}.
     * <p>
     * The fields concerned by this update operation are: {@code status}. However,
     * if the status is changing from {@code NEXT} to {@code CURRENT} then the
     * fields {@code supervisor} and {@code startedAt} must be specified and valid.
     * 
     * @param careerPositionId
     * @param jsonPatch
     * 
     * @return the updated career position.
     * 
     * @throws IllegalArgumentException if the JSON patch application fails, or if 
     * there is an attempt to set the career position status to {@code CURRENT} and 
     * its actual status is not {@code NEXT}, or the supervisor is not specified or he 
     * is not a valid candidate, or the career position starting date is not valid 
     * (preceeds the starting date of the previous career position), or there is no 
     * {@code CURRENT} career position.
     */
    public CareerPositionEntity partialUpdateCareerPosition(
            Long careerPositionId,
            JsonPatch jsonPatch);

    public CareerPositionEntity findById(Long id);
    public void deleteById(Long id);
    public CareerPositionEntity create(CareerPositionEntity careerPosition);

}
