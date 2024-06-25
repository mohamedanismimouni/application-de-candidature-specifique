package com.talan.polaris.services;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.json.JsonPatch;
import javax.mail.MessagingException;

import com.talan.polaris.dto.PasswordSubmission;
import com.talan.polaris.dto.SecretWord;
import com.talan.polaris.dto.TeamsAssignmentStatistics;
import com.talan.polaris.entities.CollaboratorEntity;
import com.talan.polaris.entities.TeamEntity;
import com.talan.polaris.enumerations.CareerPositionStatusEnum;
import com.talan.polaris.enumerations.MentorshipStatusEnum;
import com.talan.polaris.enumerations.ProfileTypeEnum;
import com.talan.polaris.exceptions.*;
import org.springframework.data.domain.Sort;

/**
 * A specific extension of {@link GenericService} containing business methods
 * definitions related to {@link CollaboratorEntity}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public interface CollaboratorService {

    /**
     * Finds a user by its email.
     * 
     * @param email of the user.
     * 
     * @return the user associated with the given email, never {@code null}.
     * 
     * @throws AccountNotFoundException if no user was found for the given email.
     */
    public CollaboratorEntity findUserByEmail(String email);


    /**
     * Finds resources (collaborators or managers) assigned to a team given by its
     * {@code teamId}, or resources unassigned to any team if the {@code teamId} is
     * {@code null} or blank.
     * 
     * @param profileType of the resources ({@link ProfileTypeEnum#COLLABORATOR} or 
     * {@link ProfileTypeEnum#MANAGER}).
     * @param teamId can be {@code null} or a blank string, if seeking unassigned 
     * resources.
     * 
     * @return the assigned or the unassigned resources.
     * 
     * @throws IllegalArgumentException if the {@code profileType} is different than 
     * {@link ProfileTypeEnum#COLLABORATOR} or {@link ProfileTypeEnum#MANAGER}.
     */
    public Collection<CollaboratorEntity> findResourcesByTeamId(
            ProfileTypeEnum profileType,
            String teamId);

    /**
     * Counts the number of assigned and unassigned collaborators and managers.
     * 
     * @return the number of assigned and unassigned collaborators and managers.
     */
    public TeamsAssignmentStatistics countTeamsAssignmentStatistics();

    /**
     * Decides based on the {@code initialized} parameter whether to fetch
     * uninitialized team members (collaborators already assigned to a team, but do
     * not already have any career position) or initialized team members by calling
     * {@link com.talan.polaris.services.MentorshipService#findInitializedTeamMembers()}.
     * 
     * @param initialized
     * @param teamId
     * @param profileId
     * @param careerStepId
     * @param supervisorId
     * @param careerPositionStatus
     * @param mentorId
     * @param mentorshipStatus
     * @param recruitedBefore
     * 
     * @return initialized team members matching the give parameters or unitialized 
     * team members if any.
     */
    public Collection<CollaboratorEntity> findTeamMembers(
            boolean initialized,
            String teamId,
            String profileId,
            String careerStepId,
            Long supervisorId,
            CareerPositionStatusEnum careerPositionStatus,
            Long mentorId,
            MentorshipStatusEnum mentorshipStatus,
            LocalDate recruitedBefore);

    /**
     * Activates the user associated with the given id, by setting his account status 
     * to {@literal ACTIVE} and initializing his password.
     * <p>
     * The session created specifically for the account activation operation is deleted 
     * at the end of the activation, whether the activation has succeeded or not.
     * 
     * @param userId of the user to be activated.
     * @param sessionId of the session created specifically for
     * the account activation operation.
     * @param passwordSubmission contains the initial password and its confirmation.
     * 
     * @throws AccountActivationException if 
     * {@link com.talan.polaris.exceptions.ResourceNotFoundException} is catched, or 
     * if the account status of the user associated with the given id is not 
     * {@literal INACTIVE}.
     */
    public void activateUserAccount(
            Long userId,
            String sessionId,
            PasswordSubmission passwordSubmission);

    /**
     * Relies on {@link com.talan.polaris.services.SessionService} to create a session
     * specifically for the account activation operation, then attempts to send an
     * account activation mail by delegating to 
     * {@link com.talan.polaris.services.MailService}.
     * 
     * @param userId of the user to whom the account activation mail will be sent.
     * 
     * @throws AccountActivationMailSendingException if the account status of the user
     * associated with the given id is not {@literal INACTIVE}.
     * 
     * @throws MailSendingException if the {@link com.talan.polaris.services.MailService} 
     * failed to send the mail for any reason.
     */
    public void sendAccountActivationMail(Long userId);

    /**
     * Relies on {@link com.talan.polaris.services.SessionService} to create a session
     * specifically for the password reset operation, then attempts to send a password 
     * reset mail by delegating to {@link com.talan.polaris.services.MailService}.
     * 
     * @param email of the user to whom the password will be reset.
     * 
     * @throws AccountNotActiveException if the account status of the user
     * associated with the given email is not {@literal ACTIVE}.
     * 
     * @throws MailSendingException if the {@link com.talan.polaris.services.MailService} 
     * failed to send the mail for any reason.
     */
    public void sendPasswordResetMail(String email);

    /**
     * Resets the password of the user associated with the given id, if his account 
     * status is set to {@literal ACTIVE}.
     * <p>
     * The session created specifically for the password reset operation is deleted 
     * at the end of the process, whether the password has been reset successfully 
     * or not.
     * 
     * @param userId of the user to whom the password will be reset.
     * @param sessionId of the session created specifically for
     * the password reset operation.
     * @param passwordSubmission contains the new password and its confirmation.
     * 
     * @throws PasswordResetException if 
     * {@link com.talan.polaris.exceptions.ResourceNotFoundException} is catched, or 
     * if the account status of the user associated with the given id is not 
     * {@literal ACTIVE}.
     */
    public void resetPassword(
            Long userId,
            String sessionId,
            PasswordSubmission passwordSubmission);

    /**
     * Updates the user's account status if it is not {@literal INACTIVE}.
     * 
     * @param userId of the user to be updated.
     * 
     * @throws AccountStatusUpdateException if the user's account is {@literal INACTIVE}.
     */
    public void updateUserAccountStatus(Long userId);

    /**
     * Assigns a collaborator to a team, in other terms, updates the {@code memberOf} 
     * property of {@link com.talan.polaris.entities.CollaboratorEntity} with the 
     * {@link com.talan.polaris.entities.TeamEntity} specified in the {@code jsonPatch}.
     * 
     * @param userId
     * @param jsonPatch
     * 
     * @return the updated collaborator.
     * 
     * @throws IllegalArgumentException if the {@link CollaboratorEntity} associated to the provided 
     * {@code userId} cannot be down casted to a
     * {@link com.talan.polaris.entities.CollaboratorEntity}, or if the JSON patch application 
     * fails, or if the provided {@link com.talan.polaris.entities.TeamEntity} is {@code null}.
     */
    public CollaboratorEntity assignCollaboratorToTeam(Long userId, JsonPatch jsonPatch);

    /**
     * Fetches the team to which the collaborator given by its {@code userId} is assigned to.
     * 
     * @param userId
     * 
     * @return the collaborator team, or {@code null} if the collaborator is not a member of
     * any team.
     * 
     * @throws IllegalArgumentException if the {@link CollaboratorEntity} associated to the provided 
     * {@code userId} cannot be down casted to a
     * {@link com.talan.polaris.entities.CollaboratorEntity}.
     */
    public TeamEntity getCollaboratorTeam(Long userId);

    /**
     * Initiates the onboarding process for a collaborator given by its {@code userId}.
     * <p>
     * Sets his secret word, and splits it to parts held by a number of his initialized more 
     * experienced team members and thus creates a number of 
     * {@link com.talan.polaris.entities.OnboardingEntity}.
     * 
     * @param userId
     * @param jsonPatch
     * 
     * @return the updated collaborator.
     * 
     * @throws IllegalArgumentException if the {@link CollaboratorEntity} associated to the provided 
     * {@code userId} cannot be down casted to a
     * {@link com.talan.polaris.entities.CollaboratorEntity}, or if the JSON patch application 
     * fails, or if the found collaborator is not an initialized team member, or if the found 
     * collaborator is out of his probationary period, or if he already has an onboarding process 
     * secret word, or if the provided secret word is empty or a non single word.
     */
    public CollaboratorEntity initiateOnboardingProcess(Long userId, JsonPatch jsonPatch);

    /**
     * Compares the provided secret word with the one stored for a collaborator given by its
     * {@code userId}.
     * <p>
     * If the secret word is matched, the the passed onboarding process flag will be set to 
     * {@code true}.
     * 
     * @param userId
     * @param secretWord
     * 
     * @throws IllegalArgumentException if the {@link CollaboratorEntity} associated to the provided 
     * {@code userId} cannot be down casted to a
     * {@link com.talan.polaris.entities.CollaboratorEntity}, or if the secret word is not 
     * matched.
     */
    public void validateSecretWord(Long userId, SecretWord secretWord);

    /**
     * Gets the secret word of a collaborator given by its {@code userId}.
     * 
     * @param userId
     * 
     * @return the secret word.
     * 
     * @throws IllegalArgumentException if the {@link CollaboratorEntity} associated to the provided 
     * {@code userId} cannot be down casted to a
     * {@link com.talan.polaris.entities.CollaboratorEntity}.
     */
    public SecretWord getSecretWord(Long userId);

    public void updateUser(CollaboratorEntity user);

    /**
     * load the list of HR responsible
     * @return collection of HR Responsible
     */
  /*  public Collection<CollaboratorEntity> getHRResponsible();*/

    /**
     * user registration method
     * @param user
     * @return created User
     */
    public CollaboratorEntity signUp(CollaboratorEntity user);


    public void assignCollaboratorsToTeam(String teamId, List<Long> collaboratorsId);

    Collection<CollaboratorEntity> findUsersByProfileType(ProfileTypeEnum profileType);

    public CollaboratorEntity findById(Long id);

    public Collection<CollaboratorEntity> findAll();

    public Collection<CollaboratorEntity> findAll(Sort sort);

    public CollaboratorEntity createUser(CollaboratorEntity CollaboratorEntity);

    public CollaboratorEntity update(CollaboratorEntity entity);

    public void deleteById(Long id);

    public void deleteInBatch(Collection<CollaboratorEntity> entities);

    public CollaboratorEntity create(CollaboratorEntity CollaboratorEntity);

    public CollaboratorEntity findCollabByMatricule(String matricule);

    public CollaboratorEntity findCollabByIdByblos(Long idByblos);
    public Boolean existsCollabEntityByMatricule(String matricule);
    public void updateCollabFunction(Long collabId, String functionLibelle);
    public CollaboratorEntity save(CollaboratorEntity collaboratorEntity) ;


    public Collection<CollaboratorEntity> findCollaboratorByEvent(Long eventId);

    Collection<CollaboratorEntity> findCollaboratorsByProfileType(ProfileTypeEnum profileType);



}
