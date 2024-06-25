//package com.talan.polaris.services.impl;
//
//import com.talan.polaris.dto.SignInCredentials;
//import com.talan.polaris.dto.XAuthToken;
//import com.talan.polaris.entities.DocumentRequestEntity;
//import com.talan.polaris.entities.ProfileTypeEntity;
//import com.talan.polaris.entities.TeamEntity;
//import com.talan.polaris.entities.UserEntity;
//import com.talan.polaris.enumerations.AccountStatusEnum;
//import com.talan.polaris.exceptions.AccountNotActiveException;
//import com.talan.polaris.exceptions.WrongPasswordException;
//import com.talan.polaris.services.AuthenticationService;
//import com.talan.polaris.services.SessionService;
//import com.talan.polaris.services.UserService;
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
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.times;
//import static org.mockito.MockitoAnnotations.initMocks;
//
//
///**
// * Unit tests class for methods implemented in {@link AuthenticationServiceImpl}.
// *
// * @author Chaima maiza
// * @since 1.0.0
// */
//class AuthenticationServiceImplUnitTests {
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private SessionService sessionService;
//
//    @Mock
//    private AuthenticationService authenticationService;
//
//    @InjectMocks
//    private AuthenticationServiceImpl authenticationServiceImpl;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @BeforeEach
//    public void setup() {
//        initMocks(this);
//    }
//
//
//    @Test
//    void signIn_givenSignInCredentials_whenPasswordIsWrong_thenThrowsWrongPasswordException() {
//        // given
//        String email = "john.doe@email.com";
//        String password = "Aa@123456789";
//        Collection<DocumentRequestEntity> documentRequestEntities = new ArrayList<>();
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
//                "$2a$10$IrYvRdaDAQLJrDOPB43NX.gDqJIpvXFM5t2JCgpco4qfV4dJ0BwHW",
//                AccountStatusEnum.ACTIVE,
//                "John",
//                "Doe",
//                documentRequestEntities,
//                profileTypeEntity,
//                recruitedAt,
//                memberOf, requests,managerOf);
//
//        SignInCredentials signInCredentials = new SignInCredentials(email, password);
//
//        doReturn(false)
//                .when(passwordEncoder)
//                .matches(any(), any());
//
//        doReturn(user)
//                .when(userService)
//                .findUserByEmail(anyString());
//
//        // when + then
//        assertThatExceptionOfType(WrongPasswordException.class)
//                .isThrownBy(() -> this.authenticationServiceImpl.signIn(signInCredentials));
//
//    }
//
//    @Test
//    void signIn_givenSignInCredentials_whenAccountStatusIsInactive_thenThrowsAccountNotActiveException() {
//        // given
//        String email = "john.doe@email.com";
//        String password = "Aa@123456789";
//        Collection<DocumentRequestEntity> documentRequestEntities = new ArrayList<>();
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
//                "$2a$10$IrYvRdaDAQLJrDOPB43NX.gDqJIpvXFM5t2JCgpco4qfV4dJ0BwHW",
//                AccountStatusEnum.ACTIVE,
//                "John",
//                "Doe",
//                documentRequestEntities,
//                profileTypeEntity,
//                recruitedAt,
//                memberOf, requests,managerOf);
//        SignInCredentials signInCredentials = new SignInCredentials(email, password);
//
//        doReturn(true)
//                .when(passwordEncoder)
//                .matches(any(), any());
//
//        doReturn(user)
//                .when(userService)
//                .findUserByEmail(anyString());
//
//        // when + then
//        assertThatExceptionOfType(AccountNotActiveException.class)
//                .isThrownBy(() -> this.authenticationServiceImpl.signIn(signInCredentials));
//
//    }
//
//    @Test
//    void signIn_givenSignInCredentials_whenCalled_thenXAuthTokenIsCreated() {
//        String email = "john.doe@email.com";
//        String password = "Aa@123456789";
//        Collection<DocumentRequestEntity> documentRequestEntities = new ArrayList<>();
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
//                "$2a$10$IrYvRdaDAQLJrDOPB43NX.gDqJIpvXFM5t2JCgpco4qfV4dJ0BwHW",
//                AccountStatusEnum.ACTIVE,
//                "John",
//                "Doe",
//                documentRequestEntities,
//                profileTypeEntity,
//                recruitedAt,
//                memberOf, requests,managerOf);
//
//        SignInCredentials signInCredentials = new SignInCredentials(email, password);
//
//        doReturn(true)
//                .when(passwordEncoder)
//                .matches(any(), any());
//
//        doReturn(user)
//                .when(userService)
//                .findUserByEmail(anyString());
//        String session = "gDqJIpvXFM5t2JCgpco4qfV4dJ0";
//
//        doReturn(session)
//                .when(sessionService)
//                .createSession(any(), any());
//
//        XAuthToken CreatedXAuthToken = this.authenticationServiceImpl.signIn(signInCredentials);
//
//        // when + then
//        assertThat(CreatedXAuthToken).isNotNull();
//
//    }
//
//
//    @Test
//    void signOut_givenSignInCredentials_whenCalled_thenUserIsSignOut() {
//        // given
//        AuthenticationService authenticationServiceSpy = spy(this.authenticationServiceImpl);
//        doNothing().when(authenticationServiceSpy).signOut();
//
//        // when
//        authenticationServiceSpy.signOut();
//
//        // then
//        verify(authenticationServiceSpy, times(1)).signOut();
//    }
//
//
//}
