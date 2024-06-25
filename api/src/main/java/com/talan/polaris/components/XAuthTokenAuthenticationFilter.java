//package com.talan.polaris.components;
//import java.io.IOException;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import com.talan.polaris.dto.RequestDetails;
//import com.talan.polaris.dto.UserAuthenticationToken;
//import com.talan.polaris.dto.XAuthTokenAuthenticationToken;
//
///**
// * XAuthTokenAuthenticationFilter.
// *
// * @author Nader Debbabi
// * @since 1.0.0
// */
//@Component
//public class XAuthTokenAuthenticationFilter extends OncePerRequestFilter {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(XAuthTokenAuthenticationFilter.class);
//    private final AuthenticationManager authenticationManager;
//
//    @Autowired
//    public XAuthTokenAuthenticationFilter(@Lazy AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String sessionID = request.getHeader("X-Auth-Token");
//
//        if (sessionID == null) {
//            LOGGER.trace("Request is not meant to be processed by the XAuthTokenAuthenticationFilter as it lacks the X-Auth-Token header");
//            SecurityContextHolder.clearContext();
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        RequestDetails requestDetails = RequestDetails.fromHttpServletRequest(request);
//
//        if (LOGGER.isDebugEnabled()) {
//            LOGGER.debug("Request is being proccessed by XAuthTokenAuthenticationFilter",requestDetails);
//        }
//
//        UserAuthenticationToken userAuthenticationToken;
//
//        try {
//
//            userAuthenticationToken = (UserAuthenticationToken) this.authenticationManager.authenticate(
//                    new XAuthTokenAuthenticationToken(sessionID, requestDetails));
//
//        } catch (AuthenticationException authenticationException) {
//
//            if (LOGGER.isDebugEnabled()) {
//                LOGGER.debug("XAuthTokenAuthenticationFilter failed to authenticate the request",authenticationException);
//            }
//            SecurityContextHolder.clearContext();
//            filterChain.doFilter(request, response);
//            return;
//
//        }
//
//        LOGGER.debug("XAuthTokenAuthenticationFilter authenticated the request successfully");
//
//        SecurityContextHolder.getContext().setAuthentication(userAuthenticationToken);
//        filterChain.doFilter(request, response);
//
//    }
//
//}
