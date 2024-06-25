//package com.talan.polaris.services.impl;
//
//import com.talan.polaris.dto.SessionDetails;
//import com.talan.polaris.dto.SignInCredentials;
//import com.talan.polaris.dto.XAuthToken;
//import com.talan.polaris.entities.UserEntity;
//import com.talan.polaris.enumerations.AccountStatusEnum;
//import com.talan.polaris.enumerations.SessionTypeEnum;
//import com.talan.polaris.exceptions.AccountNotActiveException;
//import com.talan.polaris.exceptions.WrongPasswordException;
//import com.talan.polaris.services.AuthenticationService;
////import com.talan.polaris.services.SessionService;
//import com.talan.polaris.services.UserService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
///**
// * AuthenticationServiceImpl.
// * 
// * @author Nader Debbabi
// * @since 1.0.0
// */
//@Service
//public class AuthenticationServiceImpl implements AuthenticationService {
//
//    private final UserService userService;
//    private final PasswordEncoder passwordEncoder;
////    private final SessionService sessionService;
//
//    @Autowired
//    public AuthenticationServiceImpl(UserService userService, PasswordEncoder passwordEncoder, SessionService sessionService) {
//        this.userService = userService;
//        this.passwordEncoder = passwordEncoder;
//        this.sessionService = sessionService;
//    }
//
//    @Override
//    public XAuthToken signIn(SignInCredentials signInCredentials) {
//
//        final UserEntity userEntity = this.userService.findUserByEmail(signInCredentials.getEmail());
//
//        if (!this.passwordEncoder.matches(signInCredentials.getPassword(), userEntity.getPassword()))
//            throw new WrongPasswordException();
//        
//        if (!userEntity.getAccountStatus().equals(AccountStatusEnum.ACTIVE))
//            throw new AccountNotActiveException();
//    
//        return new XAuthToken(this.sessionService.createSession(userEntity.getEmail(), SessionTypeEnum.ACCESS));
//    
//    }
//
//    @Override
//    public void signOut() {
//        this.sessionService.deleteSessionById(((SessionDetails) SecurityContextHolder.getContext().getAuthentication().getCredentials()).getSessionID());
//    }
//
//}
