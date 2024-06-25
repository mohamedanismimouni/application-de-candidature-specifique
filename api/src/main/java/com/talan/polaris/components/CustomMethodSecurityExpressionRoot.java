package com.talan.polaris.components;

import java.util.Objects;

import com.talan.polaris.dto.EvaluationDTO;
import com.talan.polaris.entities.*;
import com.talan.polaris.services.*;

import org.keycloak.KeycloakPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

/**
 * CustomMethodSecurityExpressionRoot.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot
		implements MethodSecurityExpressionOperations {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomMethodSecurityExpressionRoot.class);

	private final CollaboratorService userService;
	private final OnboardingService onboardingService;
	private final MentorshipService mentorshipService;
	private final ResponseService responseService;
	private final EvaluationService evaluationService;
	private final CareerPositionService careerPositionService;
	private final TeamService teamService;

	private Object filterObject;
	private Object returnObject;
	private Object target;

	public CustomMethodSecurityExpressionRoot(Authentication authentication, CollaboratorService userService,
			OnboardingService onboardingService, MentorshipService mentorshipService, ResponseService responseService,
			EvaluationService evaluationService, CareerPositionService careerPositionService, TeamService teamService) {

		super(authentication);
		this.userService = userService;
		this.onboardingService = onboardingService;
		this.mentorshipService = mentorshipService;
		this.responseService = responseService;
		this.evaluationService = evaluationService;
		this.careerPositionService = careerPositionService;
		this.teamService = teamService;

	}

	public boolean isSelf(String userId) {

		String authenticatedUserEmail = ((KeycloakPrincipal<?>) this.getPrincipal()).getKeycloakSecurityContext()
				.getToken().getEmail();
		
		final Long authenticatedUserId = this.userService.findUserByEmail(authenticatedUserEmail).getId();

		LOGGER.debug("isSelf invoked for authenticated user with id: " + authenticatedUserId);

		return Objects.equals(userId, authenticatedUserId.toString());

	}

	public boolean isSelfLong(Long userId) {

		String authenticatedUserEmail = ((KeycloakPrincipal<?>) this.getPrincipal()).getKeycloakSecurityContext()
				.getToken().getEmail();
		
		final Long authenticatedUserId = this.userService.findUserByEmail(authenticatedUserEmail).getId();

		LOGGER.debug("isSelf invoked for authenticated user with id: " + authenticatedUserId);

		return Objects.equals(userId, authenticatedUserId);

	}

	public boolean isManagerOfSameTeamAsCollaborator(Long collaboratorId) {

		String authenticatedUserEmail = ((KeycloakPrincipal<?>) this.getPrincipal()).getKeycloakSecurityContext()
				.getToken().getEmail();
		
		final Long managerId = this.userService.findUserByEmail(authenticatedUserEmail).getId();

		LOGGER.debug("isManagerOfSameTeamAsCollaborator invoked for manager with id: " + managerId);

		final CollaboratorEntity collaborator;

		try {
			collaborator = this.userService.findById(collaboratorId);
		} catch (ClassCastException e) {
			return false;
		}

		return collaborator.getMemberOf() != null && collaborator.getMemberOf().getManagedBy() != null
				&& Objects.equals(collaborator.getMemberOf().getManagedBy().getId(), managerId);

	}

	public boolean hasCollaboratorPassedOnboardingProcess(Long collaboratorId) {

		LOGGER.debug("hasCollaboratorPassedOnboardingProcess invoked");

		final CollaboratorEntity collaborator;

		try {
			collaborator = this.userService.findById(collaboratorId);
		} catch (ClassCastException e) {
			return false;
		}

		return collaborator.isPassedOnboardingProcess();

	}

	public boolean isSecretWordPartHolder(String onboardingId) {

		String authenticatedUserEmail = ((KeycloakPrincipal<?>) this.getPrincipal()).getKeycloakSecurityContext()
				.getToken().getEmail();
		
		final Long authenticatedUserId = this.userService.findUserByEmail(authenticatedUserEmail).getId();

		LOGGER.debug("isSecretWordPartHolder invoked for authenticated user with id: " + authenticatedUserId);

		final OnboardingEntity onboarding = this.onboardingService.findById(onboardingId);

		return onboarding.getSecretWordPartHolder() != null
				&& Objects.equals(onboarding.getSecretWordPartHolder().getId(), authenticatedUserId);

	}

	public boolean isMentorOfMentorship(Long mentorshipId) {

		String authenticatedUserEmail = ((KeycloakPrincipal<?>) this.getPrincipal()).getKeycloakSecurityContext()
				.getToken().getEmail();
		
		final Long authenticatedUserId = this.userService.findUserByEmail(authenticatedUserEmail).getId();

		LOGGER.debug("isMentorOfMentorship invoked for authenticated user with id: " + authenticatedUserId);

		final MentorshipEntity mentorship = this.mentorshipService.findById(mentorshipId);

		return mentorship.getMentor() != null && Objects.equals(mentorship.getMentor().getId(), authenticatedUserId);

	}

	public boolean isMenteeOfMentorship(Long mentorshipId) {

		String authenticatedUserEmail = ((KeycloakPrincipal<?>) this.getPrincipal()).getKeycloakSecurityContext()
				.getToken().getEmail();
		
		final Long authenticatedUserId = this.userService.findUserByEmail(authenticatedUserEmail).getId();

		LOGGER.debug("isMenteeOfMentorship invoked for authenticated user with id: " + authenticatedUserId);

		final MentorshipEntity mentorship = this.mentorshipService.findById(mentorshipId);

		return mentorship.getCareerPosition() != null && mentorship.getCareerPosition().getCollaborator() != null
				&& Objects.equals(mentorship.getCareerPosition().getCollaborator().getId(), authenticatedUserId);

	}

	public boolean hasRespondedToQuestion(String questionId) {

		String authenticatedUserEmail = ((KeycloakPrincipal<?>) this.getPrincipal()).getKeycloakSecurityContext()
				.getToken().getEmail();
		
		final Long collaboratorId = this.userService.findUserByEmail(authenticatedUserEmail).getId();

		LOGGER.debug("hasRespondedToQuestion invoked for authenticated collaborator with id: " + collaboratorId);

		return this.responseService.findResponseByQuestionIdAndCollaboratorId(questionId, collaboratorId) != null;

	}

	public boolean canCreateEvaluation(EvaluationDTO evaluation) {

		String authenticatedUserEmail = ((KeycloakPrincipal<?>) this.getPrincipal()).getKeycloakSecurityContext()
				.getToken().getEmail();
		
		final Long authenticatedUserId = this.userService.findUserByEmail(authenticatedUserEmail).getId();

		LOGGER.debug("canCreateEvaluation invoked for authenticated user with id: " + authenticatedUserId);

		final CareerPositionEntity careerPosition = this.careerPositionService
				.findById(evaluation.getCareerPosition().getId());

		return (careerPosition.getSupervisor() != null
				&& Objects.equals(careerPosition.getSupervisor().getId(), authenticatedUserId))
				|| (careerPosition.getCollaborator() != null && careerPosition.getCollaborator().getMemberOf() != null
						&& careerPosition.getCollaborator().getMemberOf().getManagedBy() != null
						&& Objects.equals(careerPosition.getCollaborator().getMemberOf().getManagedBy().getId(),
								authenticatedUserId));

	}

	public boolean canUpdateEvaluation(String evaluationId) {

		String authenticatedUserEmail = ((KeycloakPrincipal<?>) this.getPrincipal()).getKeycloakSecurityContext()
				.getToken().getEmail();
		
		final Long authenticatedUserId = this.userService.findUserByEmail(authenticatedUserEmail).getId();

		LOGGER.debug("canUpdateEvaluation invoked for authenticated user with id: " + authenticatedUserId);

		final EvaluationEntity evaluation = this.evaluationService.findById(evaluationId);

		return evaluation.getCareerPosition() != null && ((evaluation.getCareerPosition().getSupervisor() != null
				&& Objects.equals(evaluation.getCareerPosition().getSupervisor().getId(), authenticatedUserId))
				|| (evaluation.getCareerPosition().getCollaborator() != null
						&& evaluation.getCareerPosition().getCollaborator().getMemberOf() != null
						&& evaluation.getCareerPosition().getCollaborator().getMemberOf().getManagedBy() != null
						&& Objects.equals(
								evaluation.getCareerPosition().getCollaborator().getMemberOf().getManagedBy().getId(),
								authenticatedUserId)));

	}

	public boolean canUpdateCareerPosition(Long careerPositionId) {

		String authenticatedUserEmail = ((KeycloakPrincipal<?>) this.getPrincipal()).getKeycloakSecurityContext()
				.getToken().getEmail();
		
		final Long authenticatedUserId = this.userService.findUserByEmail(authenticatedUserEmail).getId();

		LOGGER.debug("canUpdateCareerPosition invoked for authenticated user with id: " + authenticatedUserId);

		final CareerPositionEntity careerPosition = this.careerPositionService.findById(careerPositionId);

		return careerPosition.getCollaborator() != null && careerPosition.getCollaborator().getMemberOf() != null
				&& careerPosition.getCollaborator().getMemberOf().getManagedBy() != null && Objects.equals(
						careerPosition.getCollaborator().getMemberOf().getManagedBy().getId(), authenticatedUserId);

	}

	public boolean canUpdateTeam(String teamId) {

		String authenticatedUserEmail = ((KeycloakPrincipal<?>) this.getPrincipal()).getKeycloakSecurityContext()
				.getToken().getEmail();
		
		final Long authenticatedUserId = this.userService.findUserByEmail(authenticatedUserEmail).getId();

		LOGGER.debug("canUpdateTeam invoked for authenticated user with id: " + authenticatedUserId);

		final TeamEntity team = this.teamService.findById(teamId);

		return team.getManagedBy() != null && Objects.equals(team.getManagedBy().getId(), authenticatedUserId);

	}

	@Override
	public void setFilterObject(Object filterObject) {
		this.filterObject = filterObject;
	}

	@Override
	public Object getFilterObject() {
		return this.filterObject;
	}

	@Override
	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}

	@Override
	public Object getReturnObject() {
		return this.returnObject;
	}

	public void setThis(Object target) {
		this.target = target;
	}

	@Override
	public Object getThis() {
		return this.target;
	}

}
