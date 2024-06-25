package com.talan.polaris.controllers;

import com.talan.polaris.dto.*;
import com.talan.polaris.entities.CollaboratorEntity;
import com.talan.polaris.entities.ProfileTypeEntity;
import com.talan.polaris.enumerations.CareerPositionStatusEnum;
import com.talan.polaris.enumerations.MentorshipStatusEnum;
import com.talan.polaris.enumerations.ProfileTypeEnum;
import com.talan.polaris.mapper.CollaboratorMapper;
import com.talan.polaris.mapper.TeamMapper;
import com.talan.polaris.mapper.UserTeamMapper;
import com.talan.polaris.repositories.CollaboratorRepository;
import com.talan.polaris.repositories.ProfileTypeRepository;
import com.talan.polaris.services.CollaboratorService;
import com.talan.polaris.utils.validation.groups.CreateValidationGroup;
import com.talan.polaris.utils.validation.groups.UpdateValidationGroup;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonPatch;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;
import static com.talan.polaris.enumerations.ProfileTypeEnum.ProfileTypeConstants.*;

/**
 * A controller defining user resource endpoints.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "api/v2/users")
@Validated
public class CollaboratorController {

    private static final String[] DEFAULT_USER_ENTITY_SORT_FIELDS_NAMES = {"firstName", "lastName"};
    private final CollaboratorService userService;
    private final ModelMapper modelMapper;



    @Autowired
    private ProfileTypeRepository profileTypeRepository;


    @Autowired
    private CollaboratorRepository collaboratorRepository;
    @GetMapping("/{idUser}/{idProfileType}")
    public boolean addUserProfileType(@PathVariable("idUser") Long idUser, @PathVariable("idProfileType") Long idProfileType){
        ProfileTypeEntity profileType = profileTypeRepository.findById(idProfileType).get();
        CollaboratorEntity collaborator = collaboratorRepository.findById(idUser).get();
        Collection<ProfileTypeEntity> profileTypeEntities = collaborator.getProfileTypeEntity();
        profileTypeEntities.add(profileType);
        collaborator.setProfileTypeEntity(profileTypeEntities);
        Collection<CollaboratorEntity> users = profileType.getUsers();
        users.add(collaborator);
        profileType.setUsers(users);
        profileTypeRepository.save(profileType);
        collaboratorRepository.save(collaborator);
        return true;
    }







    @Autowired
    public CollaboratorController(CollaboratorService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping(path = "/{userId}")
    public CollaboratorDTO getUserById(@PathVariable(value = "userId", required = true) Long userId) {
        return CollaboratorMapper.convertCollabEntityToDTO(this.userService.findById(userId), modelMapper);
    }

    @GetMapping(path = "/collaboratorsByProfileType/{type}")
    public Collection<CollaboratorDTO> getCollaboratorsByProfileType(@PathVariable(value = "type", required = true) String type){
        if(type.equals("manager")){
            return CollaboratorMapper.convertCollabListToDTO(userService.findCollaboratorsByProfileType(ProfileTypeEnum.MANAGER),modelMapper);
        }else if ((type.equals("collaborator"))) {
            return CollaboratorMapper.convertCollabListToDTO(userService.findCollaboratorsByProfileType(ProfileTypeEnum.COLLABORATOR),modelMapper);
        }else if ((type.equals("rh-responsible"))) {
            return CollaboratorMapper.convertCollabListToDTO(userService.findCollaboratorsByProfileType(ProfileTypeEnum.HR_RESPONSIBLE),modelMapper);
        }
        return null;
    }

    @GetMapping(path = "/team/{userId}")
    public CollaboratorTeamDTO getUserTeamById(@PathVariable(value = "userId", required = true) Long userId) {
        return CollaboratorMapper.convertCollabTeamEntityToDTO(this.userService.findById(userId), modelMapper);
    }

    @GetMapping(params = {"email"})
    public CollaboratorDTO getUserByEmail(@RequestParam(value = "email", required = true) String email) {
     
        return CollaboratorMapper.convertCollabEntityToDTO(this.userService.findUserByEmail(email), modelMapper);
    }

    @GetMapping(path = "/current")
    public CollaboratorDTO getCurrentUser(Authentication authentication) {
        return CollaboratorMapper.convertCollabEntityToDTO(this.userService.findUserByEmail(
                ((AuthenticatedUser) ((UserAuthenticationToken) authentication).getPrincipal()).getEmail()), modelMapper);
    }

    @GetMapping(params = {"profileType", "teamId"})
    
    public Collection<CollaboratorDTO> getResourcesByTeamId(
            @RequestParam(value = "profileType", required = true) @NotNull ProfileTypeEnum profileType,
            @RequestParam(value = "teamId", required = true) String teamId) {

        return CollaboratorMapper.convertCollabListToDTO(this.userService.findResourcesByTeamId(profileType, teamId), modelMapper);

    }
   
    @GetMapping()
     public Collection<CollaboratorDTO> getUsers() {
        return CollaboratorMapper.convertCollabListToDTO(this.userService.findAll(Sort.by(
                Sort.Direction.ASC,
                DEFAULT_USER_ENTITY_SORT_FIELDS_NAMES)), modelMapper);

    }

    @GetMapping(params = {"initialized", "teamId"})
    public Collection<CollaboratorDTO> getTeamMembers(
            @RequestParam(value = "initialized", required = true) boolean initialized,
            @RequestParam(value = "teamId", required = true) String teamId,
            @RequestParam(value = "profileId", required = false) String profileId,
            @RequestParam(value = "careerStepId", required = false) String careerStepId,
            @RequestParam(value = "supervisorId", required = false) Long supervisorId,
            @RequestParam(value = "careerPositionStatus", required = false) CareerPositionStatusEnum careerPositionStatus,
            @RequestParam(value = "mentorId", required = false) Long mentorId,
            @RequestParam(value = "mentorshipStatus", required = false) MentorshipStatusEnum mentorshipStatus,
            @RequestParam(value = "recruitedBefore", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate recruitedBefore) {

        return CollaboratorMapper.convertCollabListToDTO(this.userService.findTeamMembers(
                initialized,
                teamId,
                profileId,
                careerStepId,
                supervisorId,
                careerPositionStatus,
                mentorId,
                mentorshipStatus,
                recruitedBefore), modelMapper);

    }

    @GetMapping(path = "/teams-assignment-statistics")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "') OR hasRole('" + SUPPORT_PROFILE_TYPE + "')")
    public TeamsAssignmentStatistics getTeamsAssignmentStatistics() {
        return this.userService.countTeamsAssignmentStatistics();
    }

    @PostMapping()
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "') OR hasRole('" + SUPPORT_PROFILE_TYPE + "')")
    public CollaboratorTeamDTO createUser(@RequestBody @Validated(CreateValidationGroup.class) CollaboratorTeamDTO userDTO) {
        return UserTeamMapper.convertUserEntityToDTO(this.userService.create(UserTeamMapper.convertUserDTOToEntity(userDTO, modelMapper)), modelMapper);
    }

    @PostMapping(path = "/account-activation")
    public void activateUserAccount(
            @RequestBody @Valid PasswordSubmission passwordSubmission,
            Authentication authentication) {

        this.userService.activateUserAccount(
                ((AuthenticatedUser) ((UserAuthenticationToken) authentication).getPrincipal()).getId(),
                ((SessionDetails) ((UserAuthenticationToken) authentication).getCredentials()).getSessionID(),
                passwordSubmission);

    }

    @PostMapping(path = "/account-activation-mail/{userId}")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "') OR hasRole('" + SUPPORT_PROFILE_TYPE + "')")
    public void sendAccountActivationMail(@PathVariable(value = "userId", required = true) Long userId) {
        this.userService.sendAccountActivationMail(userId);
    }

    @PostMapping(path = "/password-reset-mail", params = {"email"})
    public void sendPasswordResetMail(@RequestParam(value = "email", required = true) String email) {
        this.userService.sendPasswordResetMail(email);
    }

    @PostMapping(path = "/password-reset")
    public void resetPassword(
            @RequestBody @Valid PasswordSubmission passwordSubmission,
            Authentication authentication) {

        this.userService.resetPassword(
                ((AuthenticatedUser) ((UserAuthenticationToken) authentication).getPrincipal()).getId(),
                ((SessionDetails) ((UserAuthenticationToken) authentication).getCredentials()).getSessionID(),
                passwordSubmission);

    }

    @PostMapping(path = "/account-status/{userId}")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "') OR hasRole('" + SUPPORT_PROFILE_TYPE + "')")
    public void updateUserAccountStatus(@PathVariable(value = "userId", required = true) Long userId) {
        this.userService.updateUserAccountStatus(userId);
    }

    @PatchMapping(path = "/{userId}/member-of", consumes = "application/json-patch+json")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "') OR hasRole('" + SUPPORT_PROFILE_TYPE + "')")
    public CollaboratorDTO assignCollaboratorToTeam(
            @PathVariable(value = "userId", required = true) Long userId,
            @RequestBody JsonPatch jsonPatch) {

        return CollaboratorMapper.convertCollabEntityToDTO(this.userService.assignCollaboratorToTeam(userId, jsonPatch), modelMapper);

    }

    @GetMapping(path = "/{userId}/member-of")
    public TeamDTO getCollaboratorTeam(@PathVariable(value = "userId", required = true) Long userId) {
    	if (this.userService.getCollaboratorTeam(userId) == null ) {return null;}
    	else { return TeamMapper.convertTeamEntityToDTO(this.userService.getCollaboratorTeam(userId), modelMapper); }

    }

    @PatchMapping(path = "/{userId}/secret-word", consumes = "application/json-patch+json")
    @PreAuthorize("isManagerOfSameTeamAsCollaborator(#userId)")
    public CollaboratorDTO initiateOnboardingProcess(
            @PathVariable(value = "userId", required = true) Long userId,
            @RequestBody JsonPatch jsonPatch) {

        return CollaboratorMapper.convertCollabEntityToDTO(this.userService.initiateOnboardingProcess(userId, jsonPatch), modelMapper);

    }

    @PostMapping(path = "/{userId}/secret-word")
    @PreAuthorize("isSelfLong(#userId)")
    public void validateSecretWord(
            @PathVariable(value = "userId", required = true) Long userId,
            @RequestBody @Valid SecretWord secretWord) {

        this.userService.validateSecretWord(userId, secretWord);

    }

    @GetMapping(path = "/{userId}/secret-word")
    @PreAuthorize("isManagerOfSameTeamAsCollaborator(#userId) OR "
            + "(isSelf(#userId) AND hasCollaboratorPassedOnboardingProcess(#userId))")
    public SecretWord getSecretWord(@PathVariable(value = "userId", required = true) Long userId) {
        return this.userService.getSecretWord(userId);
    }

    @DeleteMapping(path = "/{userId}")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "') OR hasRole('" + SUPPORT_PROFILE_TYPE + "')")
    public void deleteUser(@PathVariable(value = "userId", required = true) Long userId) {
        this.userService.deleteById(userId);
    }

    @PostMapping(path = "/update-user")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "') OR hasRole('" + SUPPORT_PROFILE_TYPE + "')")
    public void updateUser(@RequestBody @Validated(UpdateValidationGroup.class) CollaboratorDTO userDTO) {

        this.userService.updateUser(CollaboratorMapper.convertCollabDTOToEntity(userDTO, modelMapper));
    }

    @PostMapping(path = "/{teamId}/assignCollaborators")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "') OR hasRole('" + SUPPORT_PROFILE_TYPE + "')")
    public void assignCollaboratorsToTeam(@PathVariable(value = "teamId", required = true) String teamId,
                                          @RequestBody List<Long> collaboratorsId) {

        this.userService.assignCollaboratorsToTeam(teamId, collaboratorsId);
    }

    @PostMapping(path = "/updateFunction/{collabId}/{libelle}")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "') OR hasRole('" + SUPPORT_PROFILE_TYPE + "')")
    public void updateUserAccountStatus(@PathVariable(value = "collabId", required = true) Long userId,
                                        @PathVariable(value = "libelle", required = true) String libelle) {
        this.userService.updateCollabFunction(userId, libelle);
    }
}
