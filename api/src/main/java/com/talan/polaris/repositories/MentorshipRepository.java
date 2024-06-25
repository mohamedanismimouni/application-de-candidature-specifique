package com.talan.polaris.repositories;

import java.util.Collection;

import com.talan.polaris.entities.CollaboratorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.talan.polaris.entities.MentorshipEntity;
import com.talan.polaris.enumerations.CareerPositionStatusEnum;
import com.talan.polaris.enumerations.MentorshipStatusEnum;

/**
 * A specific extension of {@link GenericRepository} that may contain additional
 * data access operations tied to the {@link MentorshipEntity} domain model.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Repository
public interface MentorshipRepository  extends JpaRepository<MentorshipEntity, Long> {

    @Query("SELECT mentorship FROM MentorshipEntity AS mentorship "
            + "WHERE mentorship.careerPosition.id = :careerPositionId")
    public Collection<MentorshipEntity> findMentorshipsByCareerPositionId(
            @Param("careerPositionId") Long careerPositionId);

    @Query("SELECT mentorship FROM MentorshipEntity AS mentorship "
            + "WHERE mentorship.skill.id LIKE :skillId "
            + "AND mentorship.careerPosition.id = :careerPositionId")
    public MentorshipEntity findMentorshipBySkillIdAndCareerPositionId(
            @Param("skillId") String skillId,
            @Param("careerPositionId") Long careerPositionId);

    @Query("SELECT mentorship FROM MentorshipEntity AS mentorship "
            + "WHERE mentorship.mentor.id = :mentorId "
            + "AND mentorship.careerPosition.collaborator.id = :menteeId "
            + "AND (:mentorshipStatus IS NULL OR mentorship.status LIKE :mentorshipStatus)")
    public Collection<MentorshipEntity> findMentorshipsByMentorIdAndMenteeId(
            @Param("mentorId") Long mentorId,
            @Param("menteeId") Long menteeId,
            @Param("mentorshipStatus") MentorshipStatusEnum mentorshipStatus);

    @Query("SELECT mentorship FROM MentorshipEntity AS mentorship "
            + "WHERE mentorship.mentor.id = :mentorId")
    public Collection<MentorshipEntity> findMentorshipsByMentorId(@Param("mentorId") Long mentorId);

/* @Query("SELECT DISTINCT mentorship.careerPosition.collaborator FROM MentorshipEntity AS mentorship "
            + "WHERE mentorship.careerPosition.collaborator.memberOf.id LIKE :teamId "
            + "AND (:profileId IS NULL OR mentorship.careerPosition.profile.id LIKE :profileId) "
            + "AND (:careerStepId IS NULL OR mentorship.careerPosition.profile.careerStep.id LIKE :careerStepId) "
            + "AND (:supervisorId IS NULL OR mentorship.careerPosition.supervisor.id LIKE :supervisorId) "
            + "AND (:careerPositionStatus IS NULL OR mentorship.careerPosition.status LIKE :careerPositionStatus) "
            + "AND (:mentorId IS NULL OR mentorship.mentor.id LIKE :mentorId) "
            + "AND (:mentorshipStatus IS NULL OR mentorship.status LIKE :mentorshipStatus) "
            + "ORDER BY mentorship.careerPosition.collaborator.firstName, "
            + "mentorship.careerPosition.collaborator.lastName ASC")*/
@Query("SELECT DISTINCT mentorship.careerPosition.collaborator FROM MentorshipEntity AS mentorship "
        + "WHERE mentorship.careerPosition.collaborator.memberOf.id LIKE :teamId "
        + "AND (:profileId IS NULL OR mentorship.careerPosition.profile.id LIKE :profileId) "
        + "AND (:careerStepId IS NULL OR mentorship.careerPosition.profile.careerStep.id LIKE :careerStepId) "
        + "AND (:supervisorId IS NULL OR mentorship.careerPosition.supervisor.id = :supervisorId) "
        + "AND (:careerPositionStatus IS NULL OR mentorship.careerPosition.status LIKE :careerPositionStatus) "
        + "AND (:mentorId IS NULL OR mentorship.mentor.id = :mentorId) "
        + "AND (:mentorshipStatus IS NULL OR mentorship.status LIKE :mentorshipStatus) "
        + "ORDER BY mentorship.careerPosition.collaborator.firstName, "
        + "mentorship.careerPosition.collaborator.lastName ASC")
    public Collection<CollaboratorEntity> findInitializedTeamMembers(
            @Param("teamId") String teamId,
            @Param("profileId") String profileId,
            @Param("careerStepId") String careerStepId,
            @Param("supervisorId") Long supervisorId,
            @Param("careerPositionStatus") CareerPositionStatusEnum careerPositionStatus,
            @Param("mentorId") Long mentorId,
            @Param("mentorshipStatus") MentorshipStatusEnum mentorshipStatus);

}
