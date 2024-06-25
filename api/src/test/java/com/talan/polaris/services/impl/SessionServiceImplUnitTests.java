//package com.talan.polaris.services.impl;
//
//import com.talan.polaris.entities.UserEntity;
//import com.talan.polaris.enumerations.AccountStatusEnum;
//import com.talan.polaris.enumerations.ProfileTypeEnum;
//import com.talan.polaris.enumerations.SessionTypeEnum;
//import com.talan.polaris.services.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.session.FindByIndexNameSessionRepository;
//import org.springframework.session.Session;
//
//import java.time.Duration;
//import java.time.Instant;
//import java.util.Set;
//import java.util.UUID;
//
//import static com.talan.polaris.constants.CommonConstants.SESSION_ATTRIBUTE_SESSION_TYPE;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.*;
//import static org.mockito.MockitoAnnotations.initMocks;
///**
// * Unit tests class for methods implemented in {@link SessionServiceImpl}.
// * 
// * @author Imen Mechergui
// * @since 1.0.0
// */
//public class SessionServiceImplUnitTests {
//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private SessionServiceImpl sessionServiceImpl;
//
//    @Mock
//    private  FindByIndexNameSessionRepository<Session> sessionRepository;
//
//    @BeforeEach
//    public void setup() {
//        initMocks(this);
//    }
//
//    @Test
//    public void findSessionById_givenSessionID_whenCalled_thenCallSessionRepository() {
//        // given + when
//        String sessionId= UUID.randomUUID().toString();
//        this.sessionServiceImpl.findSessionById(sessionId);
//        //then
//        verify(this.sessionRepository, times(1)).findById(sessionId);
//    }
//
//    @Test
//    public void deleteSessionById_givenSessionID_whenCalled_thenCallSessionRepository() {
//        // given + when
//        String sessionId= UUID.randomUUID().toString();
//        this.sessionServiceImpl.deleteSessionById(sessionId);
//        //then
//        verify(this.sessionRepository, times(1)).deleteById(sessionId);;
//    }
//
//
//    @Test
//    public void createSession_givenSessionID_whenCalled_thenSessionIsCreated() {
//        // given
//        Session session=new Session() {
//            @Override
//            public String getId() {
//                return null;
//            }
//
//            @Override
//            public String changeSessionId() {
//                return null;
//            }
//
//            @Override
//            public <T> T getAttribute(String attributeName) {
//                return null;
//            }
//
//            @Override
//            public Set<String> getAttributeNames() {
//                return null;
//            }
//
//            @Override
//            public void setAttribute(String attributeName, Object attributeValue) {
//            }
//
//            @Override
//            public void removeAttribute(String attributeName) {
//
//            }
//
//            @Override
//            public Instant getCreationTime() {
//                return null;
//            }
//
//            @Override
//            public void setLastAccessedTime(Instant lastAccessedTime) {
//
//            }
//
//            @Override
//            public Instant getLastAccessedTime() {
//                return null;
//            }
//
//            @Override
//            public void setMaxInactiveInterval(Duration interval) {
//
//            }
//
//            @Override
//            public Duration getMaxInactiveInterval() {
//                return null;
//            }
//
//            @Override
//            public boolean isExpired() {
//                return false;
//            }
//        };
//        UserEntity user=new UserEntity();
//        user.setAccountStatus(AccountStatusEnum.ACTIVE);
//        user.setEmail("test@gmail.com");
//        user.setFirstName("test");
//        user.setPassword("test");
//       // user.setProfileType(ProfileTypeEnum.COLLABORATOR);
//        user.setLastName("test");
//        //find user by email
//        doReturn(user)
//                .when(userService)
//                .findUserByEmail(anyString()) ;
//        UserEntity foundUser=userService.findUserByEmail("test@gmail.com");
//        //Initialization of session
//        session.setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, foundUser.getEmail());
//        session.setAttribute(SESSION_ATTRIBUTE_SESSION_TYPE, SessionTypeEnum.ACCESS.getSessionType());
//        session.setMaxInactiveInterval(Duration.ofMinutes(SessionTypeEnum.ACCESS.getSessionMaxInactiveIntervalMin()));
//       //return session of createSession()
//        doReturn(session)
//                .when(sessionRepository)
//                .createSession() ;
//        //when
//        this.sessionServiceImpl.createSession(foundUser.getEmail(),SessionTypeEnum.ACCESS);
//        //then
//        verify(sessionRepository, times(1)).findByPrincipalName(foundUser.getEmail());
//        verify(sessionRepository, times(1)).createSession();
//        verify(sessionRepository, times(1)).save(any(Session.class));
//        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
//        assertThat(foundUser).isNotNull();
//    }
//
//}
