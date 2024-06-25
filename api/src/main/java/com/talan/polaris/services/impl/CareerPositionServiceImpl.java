package com.talan.polaris.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import javax.json.JsonException;
import javax.json.JsonPatch;
import javax.json.JsonStructure;

import com.talan.polaris.entities.*;
import com.talan.polaris.exceptions.ResourceNotFoundException;
import com.talan.polaris.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.talan.polaris.enumerations.CareerPositionStatusEnum;
import com.talan.polaris.enumerations.EvaluationStatusEnum;
import com.talan.polaris.enumerations.MentorshipStatusEnum;
import com.talan.polaris.repositories.CareerPositionRepository;

import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_CAREER_POSITION_INITIALIZED_TEAM_PROFILE_CONSTRAINT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_CAREER_POSITION_CAREER_POSITION_STATUSES_CONSTRAINT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_CAREER_POSITION_SUPERVISOR_CONSTRAINT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_CAREER_POSITION_STARTING_DATE_CONSTRAINT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_CAREER_POSITION_CAREER_POSITIONS_CHAINING_CONSTRAINT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_JSON_PATCH;

/**
 * An implementation of {@link CareerPositionService}, containing business
 * methods implementations specific to {@link CareerPositionEntity}, and may
 * override some of the common methods' implementations inherited from
 * {@link GenericServiceImpl}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Service
public class CareerPositionServiceImpl
        implements CareerPositionService {

    private final CareerPositionRepository careerPositionRepository;
    private final CollaboratorService userService;
    private final ProfileService profileService;
    private final AcquiredSkillService acquiredSkillService;
    private final RequiredSkillService requiredSkillService;
    private final MentorshipService mentorshipService;
    private final CareerPathService careerPathService;
    private final EvaluationService evaluationService;
    private final ObjectMapper objectMapper;

    @Autowired
    public CareerPositionServiceImpl(
            CareerPositionRepository repository,
            CollaboratorService userService,
            ProfileService profileService,
            AcquiredSkillService acquiredSkillService,
            RequiredSkillService requiredSkillService,
            MentorshipService mentorshipService,
            CareerPathService careerPathService,
            EvaluationService evaluationService,
            ObjectMapper objectMapper) {


        this.careerPositionRepository = repository;
        this.userService = userService;
        this.profileService = profileService;
        this.acquiredSkillService = acquiredSkillService;
        this.requiredSkillService = requiredSkillService;
        this.mentorshipService = mentorshipService;
        this.careerPathService = careerPathService;
        this.evaluationService = evaluationService;
        this.objectMapper = objectMapper;

    }

    @Override
    public Collection<CareerPositionEntity> findCareerPositionsByCollaboratorIdAndStatus(
            Long collaboratorId,
            CareerPositionStatusEnum status) {

        return this.careerPositionRepository.findCareerPositionsByCollaboratorIdAndStatus(
                collaboratorId,
                status);

    }

    @Override
    @Transactional
    public CareerPositionEntity createCareerPosition(CareerPositionEntity careerPosition) {

        final CollaboratorEntity collaborator;

        try {

            collaborator = this.userService.findById(
                    careerPosition.getCollaborator().getId());

        } catch (ClassCastException e) {
            throw new IllegalArgumentException(e);
        }
        final ProfileEntity profile = this.profileService.findProfilesByTeamId(
                collaborator.getMemberOf().getId(),
                true).stream()
                        .filter(initializedProfile -> initializedProfile.getId().equals(careerPosition.getProfile().getId()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException(
                                ERROR_BAD_REQUEST_CAREER_POSITION_INITIALIZED_TEAM_PROFILE_CONSTRAINT));

        final List<CareerPositionEntity> currentCareerPositions = new ArrayList<>(this.findCareerPositionsByCollaboratorIdAndStatus(
                collaborator.getId(),
                CareerPositionStatusEnum.CURRENT));

        if (careerPosition.getStatus().equals(CareerPositionStatusEnum.CURRENT) &&
                !currentCareerPositions.isEmpty()) {

            throw new IllegalArgumentException(
                    ERROR_BAD_REQUEST_CAREER_POSITION_CAREER_POSITION_STATUSES_CONSTRAINT);

        }

        if (careerPosition.getStatus().equals(CareerPositionStatusEnum.NEXT)) {

            if (currentCareerPositions.isEmpty()) {

                throw new IllegalArgumentException(
                        ERROR_BAD_REQUEST_CAREER_POSITION_CAREER_POSITION_STATUSES_CONSTRAINT);

            }

            if (this.careerPathService.findOutboundCareerPaths(currentCareerPositions.get(0).getProfile().getCareerStep().getId())
                    .stream()
                    .noneMatch(careerPath -> careerPath.getToCareerStep().getId().equals(profile.getCareerStep().getId()))) {

                throw new IllegalArgumentException(
                        ERROR_BAD_REQUEST_CAREER_POSITION_CAREER_POSITIONS_CHAINING_CONSTRAINT);

            }

        }

        final Collection<CollaboratorEntity>initializedTeamMembers = this.userService.findTeamMembers(
                true,
                profile.getTeam().getId(),
                null,
                null,
                null,
                CareerPositionStatusEnum.CURRENT,
                null,
                null,
                null);

        if (careerPosition.getStatus().equals(CareerPositionStatusEnum.CURRENT)) {

            if (initializedTeamMembers.stream()
                    .noneMatch(member -> (member).getRecruitedAt().isBefore(collaborator.getRecruitedAt()) &&
                            Objects.equals(member.getId(), careerPosition.getSupervisor().getId())) &&
                    !Objects.equals(careerPosition.getSupervisor().getId(), profile.getTeam().getManagedBy().getId())) {

                throw new IllegalArgumentException(
                        ERROR_BAD_REQUEST_CAREER_POSITION_SUPERVISOR_CONSTRAINT);

            }

            if (collaborator.getRecruitedAt().isAfter(careerPosition.getStartedAt())) {

                throw new IllegalArgumentException(
                        ERROR_BAD_REQUEST_CAREER_POSITION_STARTING_DATE_CONSTRAINT);

            }

        }

        if (careerPosition.getStatus().equals(CareerPositionStatusEnum.NEXT)) {

            careerPosition.setSupervisor(null);
            careerPosition.setStartedAt(null);

            final List<CareerPositionEntity> nextCareerPositions = new ArrayList<>(this.findCareerPositionsByCollaboratorIdAndStatus(
                    collaborator.getId(),
                    CareerPositionStatusEnum.NEXT));

            if (!nextCareerPositions.isEmpty()) {

                if (nextCareerPositions.get(0).getProfile().getId().equals(profile.getId())) {

                    throw new IllegalArgumentException(
                            ERROR_BAD_REQUEST_CAREER_POSITION_CAREER_POSITION_STATUSES_CONSTRAINT);

                } else {

                    this.mentorshipService.deleteInBatch(this.mentorshipService.findMentorshipsByCareerPositionId(
                            nextCareerPositions.get(0).getId()));

                    deleteById(nextCareerPositions.get(0).getId());

                }

            }

        }

        create(careerPosition);

        final Collection<RequiredSkillEntity> requiredSkills = this.requiredSkillService.findRequiredSkillsByProfileIdAndSkillType(
                careerPosition.getProfile().getId(),
                null);

        final Collection<Long> initializedTeamMembersIds = initializedTeamMembers.stream()
                .map(teamMember -> teamMember.getId())
                .collect(Collectors.toList());

        final Collection<AcquiredSkillEntity> initializedTeamMembersAcquiredSkills = this.acquiredSkillService.findAcquiredSkillsByCollaboratorsIds(
                initializedTeamMembersIds);

        requiredSkills.forEach((RequiredSkillEntity requiredSkill) -> {

            final List<CollaboratorEntity> potentialMentors = initializedTeamMembersAcquiredSkills.stream()
                    .filter(acquiredSkill -> acquiredSkill.getSkill().getId().equals(
                            requiredSkill.getSkill().getId()) &&
                                    acquiredSkill.getProgress().get(acquiredSkill.getProgress().size() - 1).getLevel().getSkillLevel().compareTo(
                                            requiredSkill.getLevel().getSkillLevel()) >= 0)
                    .sorted((AcquiredSkillEntity a, AcquiredSkillEntity b) -> a.getProgress().get(a.getProgress().size() - 1).getLevel().getSkillLevel().compareTo(
                            b.getProgress().get(b.getProgress().size() - 1).getLevel().getSkillLevel()))
                    .map(acquiredSkill -> acquiredSkill.getCollaborator())
                    .collect(Collectors.toList());

            CollaboratorEntity mentor;

            if (potentialMentors.isEmpty()) {
                mentor = profile.getTeam().getManagedBy();
            } else {
                mentor = potentialMentors.get(new Random().ints(1, 0, potentialMentors.size()).findFirst().getAsInt());
            }

            this.mentorshipService.create(new MentorshipEntity(
                    mentor,
                    requiredSkill.getSkill(),
                    careerPosition,
                    MentorshipStatusEnum.ACTIVE));

        });

        return careerPosition;

    }

    @Override
    @Transactional
    public CareerPositionEntity partialUpdateCareerPosition(
            Long careerPositionId,
            JsonPatch jsonPatch) {

        CareerPositionEntity careerPosition = findById(careerPositionId);

        JsonStructure targetJson = objectMapper.convertValue(
                new CareerPositionEntity(),
                JsonStructure.class);

        JsonStructure patchedJson;
        try {
            patchedJson = jsonPatch.apply(targetJson);
        } catch (JsonException e) {
            throw new IllegalArgumentException(ERROR_BAD_REQUEST_JSON_PATCH, e);
        }

        CareerPositionEntity patchedCareerPosition = this.objectMapper.convertValue(
                patchedJson,
                CareerPositionEntity.class);

        if ((CareerPositionStatusEnum.CURRENT).equals(patchedCareerPosition.getStatus())) {

            if (!careerPosition.getStatus().equals(CareerPositionStatusEnum.NEXT)) {

                throw new IllegalArgumentException(
                        ERROR_BAD_REQUEST_CAREER_POSITION_CAREER_POSITION_STATUSES_CONSTRAINT);

            }

            if (patchedCareerPosition.getSupervisor() == null ||
                    (this.userService.findTeamMembers(
                            true,
                            careerPosition.getCollaborator().getMemberOf().getId(),
                            null,
                            null,
                            null,
                            CareerPositionStatusEnum.CURRENT,
                            null,
                            null,
                            careerPosition.getCollaborator().getRecruitedAt())
                                    .stream()
                                    .noneMatch(member -> member.getId().equals(patchedCareerPosition.getSupervisor().getId()))) &&
                    !Objects.equals(careerPosition.getSupervisor().getId(), careerPosition.getCollaborator().getMemberOf().getManagedBy().getId())) {

                throw new IllegalArgumentException(
                        ERROR_BAD_REQUEST_CAREER_POSITION_SUPERVISOR_CONSTRAINT);

            }

            List<CareerPositionEntity> currentCareerPositions = new ArrayList<>(this.findCareerPositionsByCollaboratorIdAndStatus(
                    careerPosition.getCollaborator().getId(),
                    CareerPositionStatusEnum.CURRENT));

            if (currentCareerPositions.isEmpty()) {

                throw new IllegalArgumentException(
                        ERROR_BAD_REQUEST_CAREER_POSITION_CAREER_POSITION_STATUSES_CONSTRAINT);

            }

            if (patchedCareerPosition.getStartedAt() == null ||
                    patchedCareerPosition.getStartedAt().isBefore(currentCareerPositions.get(0).getStartedAt())) {

                throw new IllegalArgumentException(
                        ERROR_BAD_REQUEST_CAREER_POSITION_STARTING_DATE_CONSTRAINT);

            }

            currentCareerPositions.get(0).setStatus(CareerPositionStatusEnum.PREVIOUS);

            careerPosition.setStatus(CareerPositionStatusEnum.CURRENT);
            careerPosition.setSupervisor(patchedCareerPosition.getSupervisor());
            careerPosition.setStartedAt(patchedCareerPosition.getStartedAt());

            List<EvaluationEntity> openEvaluations = new ArrayList<>(
                    this.evaluationService.findEvaluations(
                            careerPosition.getCollaborator().getId(),
                            null,
                            EvaluationStatusEnum.OPEN));

            if (!openEvaluations.isEmpty()) {
                openEvaluations.get(0).setCareerPosition(careerPosition);
                openEvaluations.get(0).setSupervisorRating(null);
                openEvaluations.get(0).setSupervisorFeedback(null);
            }

        }

        return careerPosition;

    }

    @Override
    public CareerPositionEntity findById(Long id) {
        return this.careerPositionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString()));
    }

    @Override
    public void deleteById(Long id) {
        this.careerPositionRepository.delete(this.findById(id));

    }

    @Override
    public CareerPositionEntity create(CareerPositionEntity careerPosition) {
        return this.careerPositionRepository.saveAndFlush(careerPosition);    }

}
