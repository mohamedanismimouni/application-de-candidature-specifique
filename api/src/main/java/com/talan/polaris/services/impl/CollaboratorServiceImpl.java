package com.talan.polaris.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.json.JsonException;
import javax.json.JsonPatch;
import javax.json.JsonStructure;

import com.talan.polaris.entities.*;
import com.talan.polaris.exceptions.*;
import com.talan.polaris.repositories.CollaboratorRepository;
import com.talan.polaris.repositories.ProfileTypeRepository;
import com.talan.polaris.repositories.TeamRepository;
import com.talan.polaris.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talan.polaris.dto.PasswordSubmission;
import com.talan.polaris.dto.SecretWord;
import com.talan.polaris.dto.TeamsAssignmentStatistics;
import com.talan.polaris.enumerations.AccountStatusEnum;
import com.talan.polaris.enumerations.CareerPositionStatusEnum;
import com.talan.polaris.enumerations.MentorshipStatusEnum;
import com.talan.polaris.enumerations.ProfileTypeEnum;

import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_JSON_PATCH;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_ONBOARDING_PROCESS_INITIALIZED_TEAM_MEMBER_CONSTRAINT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_ONBOARDING_PROCESS_FRESH_RECRUIT_CONSTRAINT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_ONBOARDING_PROCESS_UNIQUE_INITIATION_CONSTRAINT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_ONBOARDING_PROCESS_SECRET_WORD_VALIDATION_CONSTRAINT;
import static com.talan.polaris.constants.MessagesConstants.ERROR_BAD_REQUEST_ONBOARDING_PROCESS_SECRET_WORD_NOT_MATCHED;
import static com.talan.polaris.constants.CommonConstants.COLLABORATOR_PROBATIONARY_PERIOD_IN_MONTHS;

/**
 * An implementation of {@link CollaboratorService}, containing business methods
 * implementations specific to {@link CollaboratorEntity}, and may override some of the
 * common methods' implementations inherited from {@link GenericServiceImpl}.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Service
public class CollaboratorServiceImpl implements CollaboratorService {
    @Autowired
    private ProfileTypeRepository profileTypeRepository;
    private final CollaboratorRepository userRepository;
    private final TeamRepository teamRepository;
    private final PasswordEncoder passwordEncoder;
//    private final SessionService sessionService;
//    private final MailService mailService;
    private final ObjectMapper objectMapper;
    private final MentorshipService mentorshipService;
    private final CareerPositionService careerPositionService;
    private final OnboardingService onboardingService;
    private final KeycloakService keycloakService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CollaboratorServiceImpl.class);

    private static final String DEFAULT_SORT_FIELD_NAME = "createdAt";

    private final FunctionService functionService;

    @Autowired
    public CollaboratorServiceImpl(
            CollaboratorRepository repository,
            TeamRepository teamRepository,
            PasswordEncoder passwordEncoder,
//            SessionService sessionService,
//            MailService mailService,
            ObjectMapper objectMapper,
            MentorshipService mentorshipService,
            @Lazy CareerPositionService careerPositionService,
            OnboardingService onboardingService,
            FunctionService functionService,
            KeycloakService keycloakService) {

        this.userRepository = repository;
        this.teamRepository =teamRepository;
        this.passwordEncoder = passwordEncoder;
//        this.sessionService = sessionService;
//        this.mailService = mailService;
        this.objectMapper = objectMapper;
        this.mentorshipService = mentorshipService;
        this.careerPositionService = careerPositionService;
        this.onboardingService = onboardingService;
        this.functionService = functionService;
        this.keycloakService = keycloakService;

    }

    @Override
    public CollaboratorEntity findUserByEmail(String email) {
        LOGGER.info("Find By Email");
        return this.userRepository.findUserByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException(email));
    }

    /*@Override
    public Collection<CollaboratorEntity> getHRResponsible() {
        return this.userRepository.getHRResponsible();
    }*/

    @Override
    public Collection<CollaboratorEntity> findResourcesByTeamId(
            ProfileTypeEnum profileType,
            String teamId) {
        LOGGER.info("Find Resources By Team Id");
        switch (profileType) {
            case COLLABORATOR:

            	 Collection<CollaboratorEntity> collaborators =  this.keycloakService.findUsersByClientRole(ProfileTypeEnum.COLLABORATOR)
         		.stream().map(user -> this.findUserByEmail(user.getEmail())).collect(Collectors.toList());

                if (teamId == null || ("").equals(teamId)) {

                    return collaborators.stream().filter(collaborator -> collaborator.getMemberOf() == null).collect(Collectors.toList());
                }
                return collaborators.stream().filter(collaborator -> collaborator.getMemberOf() == null ? false : collaborator.getMemberOf().getId().equals(teamId)).collect(Collectors.toList());

            case MANAGER:

            	 Collection<CollaboratorEntity> managers =  this.keycloakService.findUsersByClientRole(ProfileTypeEnum.MANAGER)
          		.stream().map(user -> this.findUserByEmail(user.getEmail())).collect(Collectors.toList());

                return managers
                        .stream()
                        .filter(user -> {
                            CollaboratorEntity manager = user;
                            if (teamId == null || ("").equals(teamId)) {
                                return manager.getManagerOf() == null;
                            } else {
                                return manager.getManagerOf() != null &&
                                        manager.getManagerOf().getId().equals(teamId);
                            }
                        })
                        .collect(Collectors.toList());
            default:
                throw new IllegalArgumentException();
        }

    }

    @Override
    public TeamsAssignmentStatistics countTeamsAssignmentStatistics() {

        int assignedCollaborators = 0;
        int unassignedCollaborators = 0;
        int assignedManagers = 0;
        int unassignedManagers = 0;
        LOGGER.info("Count Teams Assignment Statistics");
        Collection<CollaboratorEntity> users = findAll();

        for (CollaboratorEntity user : users) {
/*            for(RoleEntity role:user.getRoles()) {
                if (role.getLabel().equals(ProfileTypeEnum.COLLABORATOR)) {
                    if ((user).getMemberOf() != null) {
                        assignedCollaborators++;
                    } else {
                        unassignedCollaborators++;
                    }
                } else if (role.getLabel().equals(ProfileTypeEnum.MANAGER)) {
                    if ((user).getManagerOf() != null) {
                        assignedManagers++;
                    } else {
                        unassignedManagers++;
                    }
                }
            }*/
        }

        return new TeamsAssignmentStatistics(
                assignedCollaborators,
                unassignedCollaborators,
                assignedManagers,
                unassignedManagers);

    }

    @Override
    public Collection<CollaboratorEntity> findTeamMembers(
            boolean initialized,
            String teamId,
            String profileId,
            String careerStepId,
            Long supervisorId,
            CareerPositionStatusEnum careerPositionStatus,
            Long mentorId,
            MentorshipStatusEnum mentorshipStatus,
            LocalDate recruitedBefore) {
        LOGGER.info("Find Team Members");
        if (initialized) {

            return this.mentorshipService.findInitializedTeamMembers(
                    teamId,
                    profileId,
                    careerStepId,
                    supervisorId,
                    careerPositionStatus,
                    mentorId,
                    mentorshipStatus,
                    recruitedBefore);

        } else {

            return this.userRepository.findUninitializedTeamMembers(teamId);

        }

    }


    @Override
    @Transactional
    public CollaboratorEntity create(CollaboratorEntity CollaboratorEntity) {
        LOGGER.info("Create User");
      //  CollaboratorEntity.setPassword(this.passwordEncoder.encode(UUID.randomUUID().toString()));
        CollaboratorEntity.setAccountStatus(AccountStatusEnum.INACTIVE);
        CollaboratorEntity createdCollaboratorEntity = this.createUser(CollaboratorEntity);
//        String sessionID = this.sessionService.createSession(createdCollaboratorEntity.getEmail(),
//                SessionTypeEnum.ACCOUNT_ACTIVATION);

        try {

//            this.mailService.sendAccountActivationMail(createdCollaboratorEntity.getFirstName(), createdCollaboratorEntity.getEmail(),
//                    sessionID);

        } catch (MailSendingException e) {
//            this.sessionService.deleteSessionById(sessionID);
            throw e;

        }

        return createdCollaboratorEntity;

    }

    @Override
    @Transactional
    public void activateUserAccount(
            Long userId,
            String sessionId,
            PasswordSubmission passwordSubmission) {

        CollaboratorEntity CollaboratorEntity;
        LOGGER.info("Activate User Account");
        try {
            CollaboratorEntity = findById(userId);
        } catch (ResourceNotFoundException e) {
//            this.sessionService.deleteSessionById(sessionId);
            throw new AccountActivationException(e);
        }

        if (!CollaboratorEntity.getAccountStatus().equals(AccountStatusEnum.INACTIVE)) {
//            this.sessionService.deleteSessionById(sessionId);
            throw new AccountActivationException();
        }

       /* CollaboratorEntity.setPassword(
                this.passwordEncoder.encode(passwordSubmission.getPassword()));*/
        CollaboratorEntity.setAccountStatus(AccountStatusEnum.ACTIVE);
//        this.sessionService.deleteSessionById(sessionId);

    }


    @Override
    public void sendAccountActivationMail(Long userId) {
        LOGGER.info("Send Account Activation Mail");
        CollaboratorEntity CollaboratorEntity = findById(userId);
        if (!CollaboratorEntity.getAccountStatus().equals(AccountStatusEnum.INACTIVE))
            throw new AccountActivationMailSendingException();
//
//        String sessionId = this.sessionService.createSession(
//                CollaboratorEntity.getEmail(),
//                SessionTypeEnum.ACCOUNT_ACTIVATION);

        try {
//
//            this.mailService.sendAccountActivationMail(
//                    CollaboratorEntity.getFirstName(),
//                    CollaboratorEntity.getEmail(),
//                    sessionId);

        } catch (MailSendingException e) {
//            this.sessionService.deleteSessionById(sessionId);
            throw e;

        }

    }

    @Override
    public void sendPasswordResetMail(String email) {
        LOGGER.info("send Password Reset Mail");
        CollaboratorEntity user = this.findUserByEmail(email);
        if (!user.getAccountStatus().equals(AccountStatusEnum.ACTIVE))
            throw new AccountNotActiveException();

//        String sessionId = this.sessionService.createSession(
//                user.getEmail(),
//                SessionTypeEnum.PASSWORD_RESET);

        try {

//            this.mailService.sendPasswordResetMail(
//                    user.getFirstName(),
//                    user.getEmail(),
//                    sessionId);

        } catch (MailSendingException e) {
//            this.sessionService.deleteSessionById(sessionId);
            throw e;

        }

    }

    @Override
    @Transactional
    public void resetPassword(
            Long userId,
            String sessionId,
            PasswordSubmission passwordSubmission) {

        CollaboratorEntity user;
        LOGGER.info("Reset password");
        try {
            user = findById(userId);
        } catch (ResourceNotFoundException e) {
//            this.sessionService.deleteSessionById(sessionId);
            throw new PasswordResetException(e);
        }

        if (!user.getAccountStatus().equals(AccountStatusEnum.ACTIVE)) {
//            this.sessionService.deleteSessionById(sessionId);
            throw new PasswordResetException();
        }

     /*   user.setPassword(
                this.passwordEncoder.encode(passwordSubmission.getPassword()));*/
//        this.sessionService.deleteSessionById(sessionId);

    }

    @Override
    @Transactional
    public void updateUserAccountStatus(Long userId) {
        LOGGER.info("update User Account Status");
        CollaboratorEntity CollaboratorEntity = findById(userId);
        switch (CollaboratorEntity.getAccountStatus()) {
            case INACTIVE:
                throw new AccountStatusUpdateException();
            case ACTIVE:
                CollaboratorEntity.setAccountStatus(AccountStatusEnum.SUSPENDED);
                break;
            case SUSPENDED:
                CollaboratorEntity.setAccountStatus(AccountStatusEnum.ACTIVE);
                break;
        }

    }

    @Override
    @Transactional
    public CollaboratorEntity assignCollaboratorToTeam(Long userId, JsonPatch jsonPatch) {

        LOGGER.info("Assign Collaborator To Team");
        CollaboratorEntity collaborator;

        try {
            collaborator = findById(userId);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(e);
        }

        JsonStructure targetJson = objectMapper.convertValue(
                new CollaboratorEntity(),
                JsonStructure.class);

        JsonStructure patchedJson;
        try {
            patchedJson = jsonPatch.apply(targetJson);
        } catch (JsonException e) {
            throw new IllegalArgumentException(ERROR_BAD_REQUEST_JSON_PATCH, e);
        }

        CollaboratorEntity patchedCollaborator = this.objectMapper.convertValue(
                patchedJson,
                CollaboratorEntity.class);

        if (patchedCollaborator.getMemberOf() == null)
            throw new IllegalArgumentException();

        collaborator.setMemberOf(patchedCollaborator.getMemberOf());

        return collaborator;

    }

    @Override
    public TeamEntity getCollaboratorTeam(Long userId) {
        LOGGER.info("Get Collaborator Team");

        CollaboratorEntity collaborator;

        try {
            collaborator = findById(userId);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(e);
        }

        return collaborator.getMemberOf();

    }

    @Override
    @Transactional
    public CollaboratorEntity initiateOnboardingProcess(Long userId, JsonPatch jsonPatch) {

        CollaboratorEntity collaborator;
        LOGGER.info("initiate Onboarding Process");

        try {
            collaborator = findById(userId);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(e);
        }

        if (this.careerPositionService.findCareerPositionsByCollaboratorIdAndStatus(
                collaborator.getId(),
                CareerPositionStatusEnum.CURRENT).isEmpty()) {

            throw new IllegalArgumentException(
                    ERROR_BAD_REQUEST_ONBOARDING_PROCESS_INITIALIZED_TEAM_MEMBER_CONSTRAINT);

        }

        if (collaborator.getRecruitedAt()
                .plusMonths(COLLABORATOR_PROBATIONARY_PERIOD_IN_MONTHS)
                .isBefore(LocalDate.now())) {

            throw new IllegalArgumentException(
                    ERROR_BAD_REQUEST_ONBOARDING_PROCESS_FRESH_RECRUIT_CONSTRAINT);

        }

        if (collaborator.getSecretWord() != null) {

            throw new IllegalArgumentException(
                    ERROR_BAD_REQUEST_ONBOARDING_PROCESS_UNIQUE_INITIATION_CONSTRAINT);

        }

        JsonStructure targetJson = objectMapper.convertValue(
                new CollaboratorEntity(),
                JsonStructure.class);

        JsonStructure patchedJson;
        try {
            patchedJson = jsonPatch.apply(targetJson);
        } catch (JsonException e) {
            throw new IllegalArgumentException(ERROR_BAD_REQUEST_JSON_PATCH, e);
        }

        CollaboratorEntity patchedCollaborator = this.objectMapper.convertValue(
                patchedJson,
                CollaboratorEntity.class);

        if (patchedCollaborator.getSecretWord() == null ||
                patchedCollaborator.getSecretWord().trim().equals("") ||
                patchedCollaborator.getSecretWord().trim().contains(" ")) {

            throw new IllegalArgumentException(
                    ERROR_BAD_REQUEST_ONBOARDING_PROCESS_SECRET_WORD_VALIDATION_CONSTRAINT);

        }

        collaborator.setSecretWord(patchedCollaborator.getSecretWord().toUpperCase());
        collaborator.setPassedOnboardingProcess(false);

        List<CollaboratorEntity> potentialSecretWordPartsHolders = new ArrayList<>(this.findTeamMembers(
                true,
                collaborator.getMemberOf().getId(),
                null,
                null,
                null,
                CareerPositionStatusEnum.CURRENT,
                null,
                null,
                collaborator.getRecruitedAt())).stream()
                .filter((CollaboratorEntity holder) -> {
                    CollaboratorEntity holderCollaborator = holder;
                    return holderCollaborator.isPassedOnboardingProcess() ||
                            (!holderCollaborator.isPassedOnboardingProcess() && holderCollaborator.getSecretWord() == null);
                }).collect(Collectors.toList());

        if (potentialSecretWordPartsHolders.isEmpty())
            potentialSecretWordPartsHolders.add(collaborator.getMemberOf().getManagedBy());

        Collections.shuffle(potentialSecretWordPartsHolders);

        int secretWordPartSize = collaborator.getSecretWord().length() / potentialSecretWordPartsHolders.size();

        if ((collaborator.getSecretWord().length() % potentialSecretWordPartsHolders.size()) != 0)
            secretWordPartSize += 1;

        int secretWordPartsNumber = collaborator.getSecretWord().length() / secretWordPartSize;

        if ((collaborator.getSecretWord().length() % secretWordPartSize) != 0)
            secretWordPartsNumber += 1;

        for (int i = 0; i < secretWordPartsNumber; i++) {

            OnboardingEntity onboarding = this.onboardingService.create(new OnboardingEntity(
                    collaborator.getSecretWord().substring(i * secretWordPartSize, Math.min(
                            (i * secretWordPartSize) + secretWordPartSize,
                            collaborator.getSecretWord().length())),
                            potentialSecretWordPartsHolders.get(i),
                    collaborator));

         /*   this.mailService.sendOnboardingProcessMail(
                    onboarding.getSecretWordPartHolder().getFirstName(),
                    onboarding.getSecretWordPartHolder().getEmail(),
                    onboarding.getFreshRecruit().getFirstName()
                            + ' '
                            + onboarding.getFreshRecruit().getLastName(),
                    onboarding.getSecretWordPart());*/

        }

        return collaborator;

    }

    @Override
    @Transactional
    public void validateSecretWord(Long userId, SecretWord secretWord) {
        LOGGER.info("validate Secret Word");
        CollaboratorEntity collaborator;

        try {
            collaborator = findById(userId);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(e);
        }

        if (!secretWord.getValue().equalsIgnoreCase(collaborator.getSecretWord())) {

            throw new IllegalArgumentException(
                    ERROR_BAD_REQUEST_ONBOARDING_PROCESS_SECRET_WORD_NOT_MATCHED);

        }

        collaborator.setPassedOnboardingProcess(true);

    }

    @Override
    public SecretWord getSecretWord(Long userId) {
        LOGGER.info("Get Secret Word");

        CollaboratorEntity collaborator;

        try {
            collaborator = findById(userId);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(e);
        }

        return new SecretWord(collaborator.getSecretWord());

    }

    @Override
    public CollaboratorEntity signUp(CollaboratorEntity CollaboratorEntity) {
       // CollaboratorEntity.setPassword(this.passwordEncoder.encode(UUID.randomUUID().toString()));
        CollaboratorEntity.setAccountStatus(AccountStatusEnum.INACTIVE);
        //save user Entity
        CollaboratorEntity createdCollaboratorEntity = this.createUser(CollaboratorEntity);
        //send Activation Mail to HR responsible
        for (CollaboratorEntity userRH : this.findUsersByProfileType(ProfileTypeEnum.HR_RESPONSIBLE)) {
            if (userRH.getAccountStatus().equals(AccountStatusEnum.ACTIVE)) {
                try {
                   // this.mailService.sendRequestAccountActivationMail(userRH.getFirstName(), userRH.getEmail(), CollaboratorEntity.getFirstName() + " " + CollaboratorEntity.getLastName());
                } catch (MailSendingException e) {
                    LOGGER.error("problem of sending registration mail!");
                    throw e;

                }
            }
        }
        return createdCollaboratorEntity;

    }

    @Override
    public void updateUser(CollaboratorEntity user) {
        CollaboratorEntity CollaboratorEntity = findById(user.getId());
        CollaboratorEntity.setFirstName(user.getFirstName());
        CollaboratorEntity.setLastName(user.getLastName());
        CollaboratorEntity.setEmail(user.getEmail());
        update(CollaboratorEntity);
   /*   if (CollaboratorEntity.getProfileType().equals(user.getProfileType())) {
            CollaboratorEntity.setFirstName(user.getFirstName());
            CollaboratorEntity.setLastName(user.getLastName());
            CollaboratorEntity.setEmail(user.getEmail());
            update(CollaboratorEntity);

        } else {
            if (CollaboratorEntity.getProfileType().equals(ProfileTypeEnum.COLLABORATOR)) {
                CollaboratorEntity collab = CollaboratorEntity;
                if (collab.getMemberOf() != null) {
                    throw new DeleteAssignedCollaboratorException();
                } else {
                    this.userRepository.delete(CollaboratorEntity);
                  reCreateUser(user);
                }
            } else {
                this.userRepository.delete(CollaboratorEntity);
                reCreateUser(user);
            }
        }*/

    }


/*  public CollaboratorEntity reCreateUser(CollaboratorEntity user){
    LOGGER.info("recreate User");
    user.setPassword(this.passwordEncoder.encode(UUID.randomUUID().toString()));
    user.setAccountStatus(AccountStatusEnum.INACTIVE);
    CollaboratorEntity createdCollaboratorEntity = this.createUser(user);
   this.sessionService.createSession(createdCollaboratorEntity.getEmail(),
            SessionTypeEnum.ACCOUNT_ACTIVATION);

    return user;
}*/
    @Override
    public void assignCollaboratorsToTeam(String teamId, List<Long> collaboratorsId) {
   TeamEntity teamEntity= this.teamRepository.findById(teamId).get();
        for(int i=0; i<collaboratorsId.size(); i++){
            CollaboratorEntity collaboratorEntity= findById(collaboratorsId.get(i));
            collaboratorEntity.setMemberOf(teamEntity);
            update(collaboratorEntity);
        }
    }

    @Override
    public Collection<CollaboratorEntity> findUsersByProfileType(ProfileTypeEnum profileType) {
        return this.userRepository.findUsersByProfileType(profileType);
    }

    @Override
    public CollaboratorEntity findById(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundLongException(id));
    }

    @Override
    public Collection<CollaboratorEntity> findAll() {
        return this.findAll(Sort.by(Sort.Direction.DESC, DEFAULT_SORT_FIELD_NAME));
    }

    @Override
    public Collection<CollaboratorEntity> findAll(Sort sort) {
        return this.userRepository.findAll(sort);
    }

    @Override
    public CollaboratorEntity createUser(CollaboratorEntity CollaboratorEntity) {
        return this.userRepository.saveAndFlush(CollaboratorEntity);
    }

    @Override
    public CollaboratorEntity update(CollaboratorEntity CollaboratorEntity) {
        return this.userRepository.saveAndFlush(CollaboratorEntity);
    }

    @Override
    public void deleteById(Long id) {
        this.userRepository.delete(this.findById(id));
    }

    @Override
    public void deleteInBatch(Collection<CollaboratorEntity> entities) {
        this.userRepository.deleteInBatch(entities);
    }

    @Override
    public CollaboratorEntity findCollabByMatricule(String matricule) {
        return userRepository.findCollabByMatricule(matricule);
    }
    @Override
    public CollaboratorEntity findCollabByIdByblos(Long idByblos) {
        return userRepository.findCollabEntityByIdByblos(idByblos);
    }
    @Override
    public Boolean existsCollabEntityByMatricule(String matricule) {
        return userRepository.existsCollabEntityByMatricule(matricule);
    }

    /**
     * update colab fuction
     * @param collabId
     * @param functionLibelle
     */
    @Override
    public void updateCollabFunction(Long collabId, String functionLibelle) {
        CollaboratorEntity collab = findById(collabId);
        FunctionEntity functionCollab= this.functionService.findFunctionByLibelle(functionLibelle);
        collab.setFunction(functionCollab);
        update(collab);

    }
    @Override
    public CollaboratorEntity save(CollaboratorEntity collaboratorEntity) {
        return   this.userRepository.save(collaboratorEntity);
    }


    @Override
    public Collection<CollaboratorEntity> findCollaboratorByEvent(Long eventId) {
        return this.userRepository.findCollaboratorByEvent(eventId);
    }

    @Override
    public Collection<CollaboratorEntity> findCollaboratorsByProfileType(ProfileTypeEnum profileType) {
                ProfileTypeEntity profileType1 = profileTypeRepository.findProfileTypeByLabel(profileType).get();
                return profileType1.getUsers();
        }
}

