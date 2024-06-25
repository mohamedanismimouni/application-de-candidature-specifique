package com.talan.polaris.services.impl;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.json.JsonException;
import javax.json.JsonPatch;
import javax.json.JsonStructure;

import com.talan.polaris.entities.CollaboratorEntity;
import com.talan.polaris.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.talan.polaris.entities.MentorshipEntity;
import com.talan.polaris.enumerations.CareerPositionStatusEnum;
import com.talan.polaris.enumerations.MentorshipStatusEnum;
import com.talan.polaris.repositories.MentorshipRepository;
import com.talan.polaris.services.MentorshipService;

import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_JSON_PATCH;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_MENTORSHIP_TERMINATED_MENTORSHIP_UPDATE_CONSTRAINT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_MENTORSHIP_RATING_CONSTRAINT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_MENTORSHIP_FEEDBACK_LENGTH_CONSTRAINT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_MENTORSHIP_TERMINATING_MENTORSHIP_CONSTRAINT;
import static com.talan.polaris.constants.CommonConstants.MENTORSHIP_RATING_MIN;
import static com.talan.polaris.constants.CommonConstants.MENTORSHIP_RATING_MAX;
import static com.talan.polaris.constants.CommonConstants.MENTORSHIP_FEEDBACK_MAX_LENGTH;

/**
 * An implementation of {@link MentorshipService}, containing business methods
 * implementations specific to {@link MentorshipEntity}, and may override some
 * of the common methods' implementations inherited from
 * {@link GenericServiceImpl}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Service
public class MentorshipServiceImpl
        implements MentorshipService {

    private final MentorshipRepository mentorshipRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public MentorshipServiceImpl(
            MentorshipRepository repository,
            ObjectMapper objectMapper) {


        this.mentorshipRepository = repository;
        this.objectMapper = objectMapper;

    }

    @Override
    public Collection<MentorshipEntity> findMentorshipsByCareerPositionId(Long careerPositionId) {
        return this.mentorshipRepository.findMentorshipsByCareerPositionId(careerPositionId);
    }

    @Override
    public MentorshipEntity findMentorshipBySkillIdAndCareerPositionId(
            String skillId,
            Long careerPositionId) {

        return this.mentorshipRepository.findMentorshipBySkillIdAndCareerPositionId(
                skillId,
                careerPositionId);

    }

    @Override
    public Collection<MentorshipEntity> findMentorshipsByMentorIdAndMenteeId(
            Long mentorId,
            Long menteeId,
            MentorshipStatusEnum mentorshipStatus) {

        return this.mentorshipRepository.findMentorshipsByMentorIdAndMenteeId(
                mentorId,
                menteeId,
                mentorshipStatus);

    }

    @Override
    public Collection<MentorshipEntity> findMentorshipsByMentorId(Long mentorId) {
        return this.mentorshipRepository.findMentorshipsByMentorId(mentorId);
    }

    @Override
    public Collection<CollaboratorEntity> findInitializedTeamMembers(
            String teamId,
            String profileId,
            String careerStepId,
            Long supervisorId,
            CareerPositionStatusEnum careerPositionStatus,
            Long mentorId,
            MentorshipStatusEnum mentorshipStatus,
            LocalDate recruitedBefore) {

        Collection<CollaboratorEntity> initializedTeamMembers = this.mentorshipRepository.findInitializedTeamMembers(
                teamId,
                profileId,
                careerStepId,
                supervisorId,
                careerPositionStatus,
                mentorId,
                mentorshipStatus);

        if (recruitedBefore != null) {

            return initializedTeamMembers.stream()
                    .filter(member -> (member).getRecruitedAt().isBefore(recruitedBefore))
                    .collect(Collectors.toList());

        } else {
            return initializedTeamMembers;
        }

    }

    @Override
    @Transactional
    public MentorshipEntity evaluateMentorshipForMentor(Long mentorshipId, JsonPatch jsonPatch) {

        MentorshipEntity mentorship = findById(mentorshipId);

        if (mentorship.getStatus().equals(MentorshipStatusEnum.TERMINATED)) {

            throw new IllegalArgumentException(
                    ERROR_BAD_REQUEST_MENTORSHIP_TERMINATED_MENTORSHIP_UPDATE_CONSTRAINT);

        }

        JsonStructure targetJson = objectMapper.convertValue(
                new MentorshipEntity(),
                JsonStructure.class);

        JsonStructure patchedJson;
        try {
            patchedJson = jsonPatch.apply(targetJson);
        } catch (JsonException e) {
            throw new IllegalArgumentException(ERROR_BAD_REQUEST_JSON_PATCH, e);
        }

        MentorshipEntity patchedMentorship = this.objectMapper.convertValue(
                patchedJson,
                MentorshipEntity.class);

        if (patchedMentorship.getMentorRating() != null) {

            if (patchedMentorship.getMentorRating().compareTo(MENTORSHIP_RATING_MIN) < 0 ||
                    patchedMentorship.getMentorRating().compareTo(MENTORSHIP_RATING_MAX) > 0) {

                throw new IllegalArgumentException(ERROR_BAD_REQUEST_MENTORSHIP_RATING_CONSTRAINT);

            } else {

                mentorship.setMentorRating(patchedMentorship.getMentorRating());

            }

        }

        if (patchedMentorship.getMentorFeedback() != null) {

            if (patchedMentorship.getMentorFeedback().length() > MENTORSHIP_FEEDBACK_MAX_LENGTH) {

                throw new IllegalArgumentException(
                        ERROR_BAD_REQUEST_MENTORSHIP_FEEDBACK_LENGTH_CONSTRAINT);

            } else {

                mentorship.setMentorFeedback(patchedMentorship.getMentorFeedback());

            }

        }

        if ((MentorshipStatusEnum.TERMINATED).equals(patchedMentorship.getStatus())) {

            if (mentorship.getMentorRating() == null ||
                    mentorship.getMentorFeedback() == null ||
                    mentorship.getMenteeRating() == null ||
                    mentorship.getMenteeFeedback() == null) {

                throw new IllegalArgumentException(
                        ERROR_BAD_REQUEST_MENTORSHIP_TERMINATING_MENTORSHIP_CONSTRAINT);

            } else {
                mentorship.setStatus(MentorshipStatusEnum.TERMINATED);
            }

        }

        return mentorship;

    }

    @Override
    @Transactional
    public MentorshipEntity evaluateMentorshipForMentee(Long mentorshipId, JsonPatch jsonPatch) {

        MentorshipEntity mentorship = findById(mentorshipId);

        if (mentorship.getStatus().equals(MentorshipStatusEnum.TERMINATED)) {

            throw new IllegalArgumentException(
                    ERROR_BAD_REQUEST_MENTORSHIP_TERMINATED_MENTORSHIP_UPDATE_CONSTRAINT);

        }

        JsonStructure targetJson = objectMapper.convertValue(
                new MentorshipEntity(),
                JsonStructure.class);

        JsonStructure patchedJson;
        try {
            patchedJson = jsonPatch.apply(targetJson);
        } catch (JsonException e) {
            throw new IllegalArgumentException(ERROR_BAD_REQUEST_JSON_PATCH, e);
        }

        MentorshipEntity patchedMentorship = this.objectMapper.convertValue(
                patchedJson,
                MentorshipEntity.class);

        if (patchedMentorship.getMenteeRating() != null) {

            if (patchedMentorship.getMenteeRating().compareTo(MENTORSHIP_RATING_MIN) < 0 ||
                    patchedMentorship.getMenteeRating().compareTo(MENTORSHIP_RATING_MAX) > 0) {

                throw new IllegalArgumentException(ERROR_BAD_REQUEST_MENTORSHIP_RATING_CONSTRAINT);

            } else {

                mentorship.setMenteeRating(patchedMentorship.getMenteeRating());

            }

        }

        if (patchedMentorship.getMenteeFeedback() != null) {

            if (patchedMentorship.getMenteeFeedback().length() > MENTORSHIP_FEEDBACK_MAX_LENGTH) {

                throw new IllegalArgumentException(
                        ERROR_BAD_REQUEST_MENTORSHIP_FEEDBACK_LENGTH_CONSTRAINT);

            } else {

                mentorship.setMenteeFeedback(patchedMentorship.getMenteeFeedback());

            }

        }

        return mentorship;

    }

    @Override
    public MentorshipEntity create(MentorshipEntity careerPosition) {
        return this.mentorshipRepository.saveAndFlush(careerPosition);
    }

    @Override
    public void deleteInBatch(Collection<MentorshipEntity> mentorshipEntities) {
        this.mentorshipRepository.deleteInBatch(mentorshipEntities);
    }

    @Override
    public MentorshipEntity findById(Long id) {
        return this.mentorshipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString()));
    }

}
