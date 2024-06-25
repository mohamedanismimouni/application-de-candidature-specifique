package com.talan.polaris.components;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.talan.polaris.dto.SessionDetails;
import com.talan.polaris.dto.UserAuthenticationToken;
import static com.talan.polaris.enumerations.SessionTypeEnum.SessionTypeConstants.ACCESS_SESSION_TYPE;
import static com.talan.polaris.enumerations.SessionTypeEnum.SessionTypeConstants.ACCOUNT_ACTIVATION_SESSION_TYPE;
import static com.talan.polaris.enumerations.SessionTypeEnum.SessionTypeConstants.PASSWORD_RESET_SESSION_TYPE;

/**
 * SessionTypeBasedAccessDecider.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Component
public class SessionTypeBasedAccessDecider {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionTypeBasedAccessDecider.class);

    public boolean check(final Authentication authentication, final String sessionType) {

        LOGGER.debug("Attempting to authorize request based on its session type");

        if (authentication == null)
            throw new NullPointerException("Expecting a non null Authentication argument");

        if (!UserAuthenticationToken.class.isAssignableFrom(authentication.getClass()))
            throw new IllegalArgumentException("Unsupported Authentication object type");

        if (sessionType == null)
            throw new NullPointerException("Expecting a non null String argument");

        if (!sessionType.equals(ACCESS_SESSION_TYPE) &&
                !sessionType.equals(ACCOUNT_ACTIVATION_SESSION_TYPE) &&
                !sessionType.equals(PASSWORD_RESET_SESSION_TYPE))
            throw new IllegalArgumentException("Invalid session type argument");

        final UserAuthenticationToken userAuthenticationToken = (UserAuthenticationToken) authentication;
        final SessionDetails sessionDetails = (SessionDetails) userAuthenticationToken.getCredentials();

        return sessionType.equals(sessionDetails.getSessionType());

    }

}
