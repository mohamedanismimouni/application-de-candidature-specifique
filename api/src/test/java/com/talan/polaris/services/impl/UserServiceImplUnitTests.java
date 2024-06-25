//package com.talan.polaris.services.impl;
//import com.talan.polaris.dto.PasswordSubmission;
//import com.talan.polaris.dto.TeamsAssignmentStatistics;
//import com.talan.polaris.entities.*;
//import com.talan.polaris.enumerations.AccountStatusEnum;
//import com.talan.polaris.enumerations.CareerPositionStatusEnum;
//import com.talan.polaris.enumerations.MentorshipStatusEnum;
//import com.talan.polaris.enumerations.ProfileTypeEnum;
//import com.talan.polaris.exceptions.*;
//import com.talan.polaris.repositories.UserRepository;
//import com.talan.polaris.services.MailService;
//import com.talan.polaris.services.SessionService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.time.Instant;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Optional;
//import java.util.UUID;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.*;
//import static org.mockito.MockitoAnnotations.initMocks;
//
///**
// * Unit tests class for methods implemented in {@link UserServiceImpl}.
// *
// * @author Nader Debbabi
// * @since 1.0.0
// */
//public class UserServiceImplUnitTests {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Mock
//    private SessionService sessionService;
//
//    @Mock
//    private MailService mailService;
//
//    @InjectMocks
//    private UserServiceImpl userServiceImpl;
//
//    @BeforeEach
//    public void setup() {
//        initMocks(this);
//    }
//
//    @Test
//    public void findUserByEmail_givenAnEmail_whenUserIsNotFound_thenThrowsAccountNotFoundException() {
//
//        // given
//        given(this.userRepository.findUserByEmail(anyString()))
//                .willReturn(Optional.ofNullable(null));
//
//        // when + then
//        assertThatExceptionOfType(AccountNotFoundException.class)
//                .isThrownBy(() -> this.userServiceImpl.findUserByEmail(""));
//
//    }
//
//    @Test
//    public void findUserByEmail_givenAnEmail_whenUserIsFound_thenUserIsFound() {
//
//        // given
//        String email = "john.doe@email.com";
//        Collection<DocumentRequestEntity> documentRequests = new ArrayList<>();
//        Collection<ProfileTypeEntity> profileTypeEntity = new ArrayList<>();
//        TeamEntity memberOf = new TeamEntity();
//        TeamEntity managerOf = new TeamEntity();
//        Collection<DocumentRequestEntity> requests = new ArrayList<>();
//        LocalDate recruitedAt = LocalDate.now();
//        UserEntity user = new UserEntity(
//                1L,
//                Instant.now(),
//                Instant.now(),
//                email,
//                "password",
//                AccountStatusEnum.ACTIVE,
//                "John",
//                "Doe",
//                documentRequests,
//                profileTypeEntity,
//                recruitedAt,
//                memberOf, requests,managerOf);
//
//        given(this.userRepository.findUserByEmail(email))
//                .willReturn(Optional.of(user));
//
//        // when
//        UserEntity foundUser = this.userServiceImpl.findUserByEmail(email);
//
//        // then
//        assertThat(foundUser).isNotNull().isEqualToComparingFieldByField(user);
//
//    }
//
//    @Test
//    public void activateUserAccount_givenUserIDSessionIDAndPasswordSubmission_whenResourceNotFoundExceptionIsCatched_thenSessionIsDeleted() {
//
//        // given
//        UserServiceImpl userServiceImplSpy = spy(this.userServiceImpl);
//        doThrow(ResourceNotFoundException.class)
//                .when(userServiceImplSpy)
//                .findById(anyLong());
//
//        // when + then
//        assertThatExceptionOfType(AccountActivationException.class)
//                .isThrownBy(() -> userServiceImplSpy.activateUserAccount(
//                        anyLong(),
//                        anyString(),
//                        any(PasswordSubmission.class)));
//
//        verify(this.sessionService, only()).deleteSessionById(anyString());
//
//    }
//
//    @Test
//    public void activateUserAccount_givenUserIDSessionIDAndPasswordSubmission_whenAccountStatusIsNotInactive_thenSessionIsDeleted() {
//
//        // given
//        UserServiceImpl userServiceImplSpy = spy(this.userServiceImpl);
//
//        UserEntity activeUser = new UserEntity();
//        activeUser.setAccountStatus(AccountStatusEnum.ACTIVE);
//
//        doReturn(activeUser)
//                .when(userServiceImplSpy)
//                .findById(anyLong());
//
//        // when + then
//        assertThatExceptionOfType(AccountActivationException.class)
//                .isThrownBy(() -> userServiceImplSpy.activateUserAccount(
//                        anyLong(),
//                        anyString(),
//                        any(PasswordSubmission.class)));
//
//        verify(this.sessionService, only()).deleteSessionById(anyString());
//
//    }
//
//    @Test
//    public void activateUserAccount_givenUserIDSessionIDAndPasswordSubmission_whenUserIsFoundAndAccountStatusIsInactiveAndUpdateIsSuccessful_thenAccountStatusIsActivatedAndSessionIsDeleted() {
//
//        // given
//        UserServiceImpl userServiceImplSpy = spy(this.userServiceImpl);
//
//        UserEntity inactiveUser = new UserEntity();
//        inactiveUser.setAccountStatus(AccountStatusEnum.INACTIVE);
//
//        doReturn(inactiveUser)
//                .when(userServiceImplSpy)
//                .findById(anyLong());
//
//        // when
//        userServiceImplSpy.activateUserAccount(
//                1L,
//                "",
//                new PasswordSubmission("", ""));
//
//        // then
//        verify(this.sessionService, only()).deleteSessionById(anyString());
//
//    }
//
//    @Test
//    public void sendAccountActivationMail_givenUserID_whenAccountStatusIsNotInactive_thenThrowsAccountActivationMailSendingException() {
//
//        //given
//        UserServiceImpl userServiceImplSpy = spy(this.userServiceImpl);
//
//        UserEntity activeUser = new UserEntity();
//        activeUser.setAccountStatus(AccountStatusEnum.ACTIVE);
//
//        doReturn(activeUser)
//                .when(userServiceImplSpy)
//                .findById(anyLong());
//
//        // when + then
//        assertThatExceptionOfType(AccountActivationMailSendingException.class)
//                .isThrownBy(() -> userServiceImplSpy.sendAccountActivationMail(anyLong()));
//
//    }
//
//    @Test
//    public void sendAccountActivationMail_givenUserID_whenMailSendingExceptionIsCatched_thenSessionIsDeleted() {
//
//        //given
//        UserServiceImpl userServiceImplSpy = spy(this.userServiceImpl);
//
//        UserEntity inactiveUser = new UserEntity();
//        inactiveUser.setAccountStatus(AccountStatusEnum.INACTIVE);
//
//        doReturn(inactiveUser)
//                .when(userServiceImplSpy)
//                .findById(anyLong());
//
//        doThrow(MailSendingException.class)
//                .when(this.mailService)
//                .sendAccountActivationMail(any(), any(), any());
//
//        // when + then
//        assertThatExceptionOfType(MailSendingException.class)
//                .isThrownBy(() -> userServiceImplSpy.sendAccountActivationMail(anyLong()));
//
//        verify(this.sessionService, times(1)).deleteSessionById(any());
//
//    }
//
//    @Test
//    public void sendAccountActivationMail_givenUserID_whenAccountStatusIsNotInactive_thenAccountActivationMailIsSent() {
//
//        //given
//        UserServiceImpl userServiceImplSpy = spy(this.userServiceImpl);
//
//        UserEntity inactiveUser = new UserEntity();
//        inactiveUser.setAccountStatus(AccountStatusEnum.INACTIVE);
//
//        doReturn(inactiveUser)
//                .when(userServiceImplSpy)
//                .findById(anyLong());
//
//        // when
//        userServiceImplSpy.sendAccountActivationMail(anyLong());
//
//        // then
//        verify(this.mailService, only()).sendAccountActivationMail(any(), any(), any());
//
//    }
//
//    @Test
//    public void updateUserAccountStatus_givenUserID_whenAccountStatusIsInactive_thenThrowsAccountStatusUpdateException() {
//
//        // given
//        UserServiceImpl userServiceImplSpy = spy(this.userServiceImpl);
//
//        UserEntity inactiveUser = new UserEntity();
//        inactiveUser.setAccountStatus(AccountStatusEnum.INACTIVE);
//
//        doReturn(inactiveUser)
//                .when(userServiceImplSpy)
//                .findById(anyLong());
//
//        // when + then
//        assertThatExceptionOfType(AccountStatusUpdateException.class)
//                .isThrownBy(() -> userServiceImplSpy.updateUserAccountStatus(anyLong()));
//
//    }
//
//    @Test
//    public void updateUserAccountStatus_givenUserID_whenAccountStatusIsActive_thenAccountStatusIsSuspended() {
//
//        // given
//        UserServiceImpl userServiceImplSpy = spy(this.userServiceImpl);
//
//        UserEntity activeUser = new UserEntity();
//        activeUser.setAccountStatus(AccountStatusEnum.ACTIVE);
//
//        doReturn(activeUser)
//                .when(userServiceImplSpy)
//                .findById(anyLong());
//
//        // when
//        userServiceImplSpy.updateUserAccountStatus(anyLong());
//
//        // then
//        assertThat(activeUser.getAccountStatus()).isEqualTo(AccountStatusEnum.SUSPENDED);
//
//    }
//
//    @Test
//    public void updateUserAccountStatus_givenUserID_whenAccountStatusIsSuspended_thenAccountStatusIsActivated() {
//
//        // given
//        UserServiceImpl userServiceImplSpy = spy(this.userServiceImpl);
//
//        UserEntity suspendedUser = new UserEntity();
//        suspendedUser.setAccountStatus(AccountStatusEnum.SUSPENDED);
//
//        doReturn(suspendedUser)
//                .when(userServiceImplSpy)
//                .findById(anyLong());
//
//        // when
//        userServiceImplSpy.updateUserAccountStatus(anyLong());
//
//        // then
//        assertThat(suspendedUser.getAccountStatus()).isEqualTo(AccountStatusEnum.ACTIVE);
//
//    }
//
//    @Test
//    public void findResourcesByTeamId_givenUserIDAndProfileType_whenResourceIsFound_thenResourcesAreFound() {
//        // given+when
//        String idTeam = UUID.randomUUID().toString();
//        Collection<UserEntity> resourcesList = this.userServiceImpl.findResourcesByTeamId(ProfileTypeEnum.COLLABORATOR, idTeam);
//        // then
//        verify(this.userRepository, times(1)).findAssignedCollaborators(ProfileTypeEnum.COLLABORATOR, idTeam);
//        assertThat(resourcesList).isNotNull();
//    }
//
//    @Test
//    public void countTeamsAssignmentStatistics_whenCalled_thenTeamsAssignmentStatisticsIsCounted() {
//        // given
//        UserServiceImpl userServiceImplSpy = spy(this.userServiceImpl);
//        // when
//        TeamsAssignmentStatistics count = userServiceImplSpy.countTeamsAssignmentStatistics();
//        // then
//        verify(userServiceImplSpy, times(1)).findAll();
//        assertThat(count).isNotNull();
//    }
//
//    @Test
//    public void findTeamMembers_givenUserIDAndTeamIDAndProfileTypeDMentorshipIDAndMentorshipStatus_whenCalled_thenTeamMembersAreFound() {
//        // given
//        String teamId = UUID.randomUUID().toString();
//        String profileId = UUID.randomUUID().toString();
//        String careerStepId = UUID.randomUUID().toString();
//        Long supervisorId= 1L;
//        Long mentorId= 1L;;
//        // when
//        Collection<UserEntity> usersList = this.userServiceImpl.findTeamMembers(false,
//                teamId,
//                profileId,
//                careerStepId,
//                supervisorId,
//                CareerPositionStatusEnum.CURRENT,
//                mentorId,
//                MentorshipStatusEnum.ACTIVE,
//                LocalDate.now());
//        // then
//        verify(userRepository, times(1)).findUninitializedTeamMembers(ProfileTypeEnum.COLLABORATOR, teamId);
//        assertThat(usersList).isNotNull();
//    }
//
//    @Test
//    public void getCollaboratorTeam_givenUserID_whenCalled_thenTeamIsFound() {
//        // given
//        Long userID = 1L;
//        UserServiceImpl userServiceImplSpy = spy(this.userServiceImpl);
//        doReturn(new TeamEntity())
//                .when(userServiceImplSpy).getCollaboratorTeam(anyLong());
//        // when
//        TeamEntity teamEntity = userServiceImplSpy.getCollaboratorTeam(userID);
//        // then
//        verify(userServiceImplSpy, times(1)).getCollaboratorTeam(userID);
//        assertThat(teamEntity).isNotNull();
//    }
//
//    @Test
//    public void signUp_givenCollaboratorEntity_whenCalled_thenCollaboratorIsRegistered() {
//        //Manager Entity
//        UserEntity collaborator=new UserEntity();
//     collaborator.setId(1L);
//        collaborator.setFirstName("test");
//        collaborator.setLastName("test");
//        //collaborator.setProfileType(ProfileTypeEnum.COLLABORATOR);
//        collaborator.setCreatedAt(Instant.now());
//        collaborator.setUpdatedAt(Instant.now());
//        collaborator.setPassword("password");
//        collaborator.setEmail("test@gmail.com");
//
//        UserServiceImpl userServiceImplSpy = spy(this.userServiceImpl);
//        //get result from request
//        doAnswer((invocation) -> invocation.getArgument(0))
//                .when(userServiceImplSpy)
//                .signUp(any(UserEntity.class));
//        // when
//        UserEntity createdUser= userServiceImplSpy.signUp(collaborator);
//        // then
//        assertThat(createdUser).isNotNull();
//        assertThat(collaborator.getFirstName()).isEqualTo(createdUser.getFirstName());
//    }
//
//
//    @Test
//    public void updateUser_givenUser_whenCalled_thenUserIsUpdated() {
//        // given
//        String email = "john.doe@email.com";
//        Collection<DocumentRequestEntity> documentRequests = new ArrayList<>();
//        Collection<ProfileTypeEntity> profileTypeEntity = new ArrayList<>();
//        TeamEntity memberOf = new TeamEntity();
//        TeamEntity managerOf = new TeamEntity();
//        Collection<DocumentRequestEntity> requests = new ArrayList<>();
//        LocalDate recruitedAt = LocalDate.now();
//        UserEntity user = new UserEntity(
//                1L,
//                Instant.now(),
//                Instant.now(),
//                email,
//                "password",
//                AccountStatusEnum.ACTIVE,
//                "John",
//                "Doe",
//                documentRequests,
//                profileTypeEntity,
//                recruitedAt,
//                memberOf, requests,managerOf);
//
//        //spy teamServiceImpl
//        UserServiceImpl userServiceImplSpy = spy(this.userServiceImpl);
//        //get result from request
//        doAnswer((invocation) -> invocation.getArgument(0))
//                .when(userServiceImplSpy)
//                .updateUser(any(UserEntity.class));
//        // when
//        userServiceImplSpy.updateUser(user);
//
//    }
//}
