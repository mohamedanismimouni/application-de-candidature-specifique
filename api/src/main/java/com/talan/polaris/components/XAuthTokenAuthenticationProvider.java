//package com.talan.polaris.components;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.core.Authentication;
//import org.springframework.session.FindByIndexNameSessionRepository;
//import org.springframework.session.Session;
//import org.springframework.stereotype.Component;
//
//import com.talan.polaris.constants.CommonConstants;
//import com.talan.polaris.dto.AuthenticatedUser;
//import com.talan.polaris.dto.RequestDetails;
//import com.talan.polaris.dto.SessionDetails;
//import com.talan.polaris.dto.UserAuthenticationToken;
//import com.talan.polaris.dto.XAuthTokenAuthenticationToken;
//import com.talan.polaris.entities.UserEntity;
//import com.talan.polaris.enumerations.AccountStatusEnum;
//import com.talan.polaris.exceptions.AccountNotFoundException;
//import com.talan.polaris.services.SessionService;
//import com.talan.polaris.services.UserService;
//
///**
// * XAuthTokenAuthenticationProvider.
// *
// * @author Nader Debbabi
// * @since 1.0.0
// */
//@Component
//public class XAuthTokenAuthenticationProvider implements AuthenticationProvider {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(XAuthTokenAuthenticationProvider.class);
//
//    private final SessionService sessionService;
//    private final UserService userService;
//
//    @Autowired
//    public XAuthTokenAuthenticationProvider(SessionService sessionService, UserService userService) {
//        this.sessionService = sessionService;
//        this.userService = userService;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) {
//
//        LOGGER.debug("XAuthTokenAuthenticationProvider authenticate method is invoked...");
//
//        if (!this.supports(authentication.getClass()))
//            return null;
//
//        final XAuthTokenAuthenticationToken xAuthTokenAuthenticationToken = (XAuthTokenAuthenticationToken) authentication;
//        final String sessionID = (String) xAuthTokenAuthenticationToken.getCredentials();
//
//        final Session session = this.sessionService.findSessionById(sessionID);
//
//        if (session == null)
//            throw new BadCredentialsException("No session was found for the provided session id");
//
//        if (session.isExpired())
//            throw new BadCredentialsException("The session associated to the provided session id is expired");
//
//        UserEntity userEntity;
//        String sessionType;
//
//        try {
//            userEntity = this.userService.findUserByEmail(
//                    session.getRequiredAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME));
//            sessionType = session.getRequiredAttribute(CommonConstants.SESSION_ATTRIBUTE_SESSION_TYPE);
//        } catch (IllegalArgumentException | AccountNotFoundException e) {
//            this.sessionService.deleteSessionById(sessionID);
//            throw new BadCredentialsException("The session associated to the provided session id is invalid");
//        }
//
//        if (userEntity.getAccountStatus().equals(AccountStatusEnum.SUSPENDED)) {
//            this.sessionService.deleteSessionById(sessionID);
//            throw new DisabledException("The session associated to the provided session id belongs to a suspended user account");
//        }
//
//        return new UserAuthenticationToken(
//                AuthenticatedUser.fromUserEntity(userEntity),
//                new SessionDetails(sessionID, sessionType),
//                (RequestDetails) xAuthTokenAuthenticationToken.getDetails(),
//                userEntity
//        );
//
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return XAuthTokenAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//
//}
