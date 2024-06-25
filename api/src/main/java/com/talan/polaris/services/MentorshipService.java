package com.talan.polaris.services;

import java.time.LocalDate;
import java.util.Collection;

import javax.json.JsonPatch;

import com.talan.polaris.entities.CollaboratorEntity;
import com.talan.polaris.entities.MentorshipEntity;
import com.talan.polaris.enumerations.CareerPositionStatusEnum;
import com.talan.polaris.enumerations.MentorshipStatusEnum;

/**
 * A specific extension of {@link GenericService} containing business methods
 * definitions related to {@link MentorshipEntity}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public interface MentorshipService{

    /**
     * Find mentorships having the given {@code careerPositionId}.
     * 
     * @param careerPositionId
     * 
     * @return the mentorships matching the given parameters if any.
     */
    public Collection<MentorshipEntity> findMentorshipsByCareerPositionId(Long careerPositionId);

    /**
     * Finds the mentorship associated to a skill given by its {@code skillId} and a
     * career position given by its {@code careerPositionId}.
     * 
     * @param skillId
     * @param careerPositionId
     * 
     * @return the mentorship matching the given parameters if any.
     */
    public MentorshipEntity findMentorshipBySkillIdAndCareerPositionId(
            String skillId,
            Long careerPositionId);

    /**
     * Find mentorships having the given {@code mentorId} and {@code menteeId} with
     * the given {@code mentorshipStatus} if specified.
     * 
     * @param mentorId
     * @param menteeId
     * @param mentorshipStatus optional
     * 
     * @return the mentorships matching the given parameters if any.
     */
    public Collection<MentorshipEntity> findMentorshipsByMentorIdAndMenteeId(
            Long mentorId,
            Long menteeId,
            MentorshipStatusEnum mentorshipStatus);

    /**
     * Find mentorships having the given {@code mentorId}.
     * 
     * @param mentorId
     * 
     * @return the mentorships matching the given parameter if any.
     */
    public Collection<MentorshipEntity> findMentorshipsByMentorId(Long mentorId);

    /**
     * Finds initialized team members, (collaborators having at least one
     * mentorship, eventually at least one career position).
     * <p>
     * The parameters are optional, except for the {@code teamId}, and used to
     * search specific collaborators from those who are initialized already.
     * 
     * @param teamId
     * @param profileId
     * @param careerStepId
     * @param supervisorId
     * @param careerPositionStatus
     * @param mentorId
     * @param mentorshipStatus
     * @param recruitedBefore
     * 
     * @return the collaborators matching the given parameters if any.
     */
    public Collection<CollaboratorEntity> findInitializedTeamMembers(
            String teamId,
            String profileId,
            String careerStepId,
            Long supervisorId,
            CareerPositionStatusEnum careerPositionStatus,
            Long mentorId,
            MentorshipStatusEnum mentorshipStatus,
            LocalDate recruitedBefore);

    /**
     * Partially updates a {@link MentorshipEntity} given by its
     * {@code mentorshipId}, by modifying the mentor evaluation properties.
     * <p>
     * The only fields affected by this partial update are: {@code status},
     * {@code mentorRating} and {@code mentorFeedback}.
     * <p>
     * Not all of those fields are required to be update at once.
     * 
     * @param mentorshipId
     * @param jsonPatch
     * 
     * @return the updated mentorship.
     * 
     * @throws IllegalArgumentException if the mentorship to be updated have a 
     * {@code TERMINATED} status, or if the JSON patch application 
     * fails, or if the specified mentor rating is not a number between
     * {@link com.talan.polaris.constants.CommonConstants#MENTORSHIP_RATING_MIN} and
     * {@link com.talan.polaris.constants.CommonConstants#MENTORSHIP_RATING_MAX}, or 
     * if the specified mentor feedback length exceeds 
     * {@link com.talan.polaris.constants.CommonConstants#MENTORSHIP_FEEDBACK_MAX_LENGTH},
     * or if there is an attempt to terminate the mentorship while the mentor and 
     * mentee ratings and feedbacks are not setted.
     */
    public MentorshipEntity evaluateMentorshipForMentor(Long mentorshipId, JsonPatch jsonPatch);

    /**
     * Partially updates a {@link MentorshipEntity} given by its
     * {@code mentorshipId}, by modifying the mentee evaluation properties.
     * <p>
     * The only fields affected by this partial update are: {@code menteeRating} 
     * and {@code menteeFeedback}.
     * <p>
     * Not all of those fields are required to be update at once.
     * 
     * @param mentorshipId
     * @param jsonPatch
     * 
     * @return the updated mentorship.
     * 
     * @throws IllegalArgumentException if the mentorship to be updated have a 
     * {@code TERMINATED} status, or if the JSON patch application 
     * fails, or if the specified mentee rating is not a number between
     * {@link com.talan.polaris.constants.CommonConstants#MENTORSHIP_RATING_MIN} and
     * {@link com.talan.polaris.constants.CommonConstants#MENTORSHIP_RATING_MAX}, or 
     * if the specified mentee feedback length exceeds 
     * {@link com.talan.polaris.constants.CommonConstants#MENTORSHIP_FEEDBACK_MAX_LENGTH}.
     */
    public MentorshipEntity evaluateMentorshipForMentee(Long mentorshipId, JsonPatch jsonPatch);


    public MentorshipEntity create(MentorshipEntity careerPosition);
    public void deleteInBatch(Collection<MentorshipEntity> entities);
    public MentorshipEntity findById(Long id);


}
