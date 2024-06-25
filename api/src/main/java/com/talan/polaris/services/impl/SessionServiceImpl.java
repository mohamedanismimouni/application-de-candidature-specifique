//package com.talan.polaris.services.impl;
//import java.time.Duration;
//import java.util.List;
//import java.util.stream.Collectors;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.session.FindByIndexNameSessionRepository;
//import org.springframework.session.Session;
//import org.springframework.stereotype.Service;
//import com.talan.polaris.enumerations.SessionTypeEnum;
//import com.talan.polaris.services.SessionService;
//import static com.talan.polaris.constants.CommonConstants.SESSION_ATTRIBUTE_SESSION_TYPE;
//
///**
// * SessionServiceImpl.
// *
// * @author Nader Debbabi
// * @since 1.0.0
// */
//@Service
//public class SessionServiceImpl<S extends Session> implements SessionService {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(SessionServiceImpl.class);
//
//    private final FindByIndexNameSessionRepository<S> sessionRepository;
//
//    @Autowired
//    public SessionServiceImpl(FindByIndexNameSessionRepository<S> sessionRepository) {
//        this.sessionRepository = sessionRepository;
//    }
//
//    @Override
//    public Session findSessionById(String sessionId) {
//        return this.sessionRepository.findById(sessionId);
//    }
//
//    @Override
//    public String createSession(String email, SessionTypeEnum sessionType) {
//
//        List<S> existingSameTypeSessions = this.sessionRepository.findByPrincipalName(email)
//                .values().stream()
//                .filter(existingSession ->
//                        sessionType.getSessionType().equals(
//                                existingSession.getAttribute(SESSION_ATTRIBUTE_SESSION_TYPE)) &&
//                                !existingSession.isExpired()
//                )
//                .collect(Collectors.toList());
//
//        if (LOGGER.isDebugEnabled())
//            LOGGER.debug(
//                    "Found " +
//                            existingSameTypeSessions.size() +
//                            " non expired existing sessions of type " +
//                            sessionType.getSessionType() +
//                            " for principal name " +
//                            email
//            );
//
//        if (!existingSameTypeSessions.isEmpty()) {
//            LOGGER.debug("Returning an existing session's id");
//            return existingSameTypeSessions.get(0).getId();
//        } else {
//            LOGGER.debug("Creating a new session and returning its id");
//            S newSession = this.sessionRepository.createSession();
//            newSession.setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, email);
//            newSession.setAttribute(SESSION_ATTRIBUTE_SESSION_TYPE, sessionType.getSessionType());
//            newSession.setMaxInactiveInterval(Duration.ofMinutes(sessionType.getSessionMaxInactiveIntervalMin()));
//            this.sessionRepository.save(newSession);
//            return newSession.getId();
//        }
//
//    }
//
//    @Override
//    public void deleteSessionById(String sessionId) {
//        this.sessionRepository.deleteById(sessionId);
//    }
//
//}
