package com.talan.polaris.enumerations;

import static com.talan.polaris.enumerations.SessionTypeEnum.SessionTypeConstants.*;

/**
 * SessionTypeEnum.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public enum SessionTypeEnum {

    ACCESS(ACCESS_SESSION_TYPE, ACCESS_SESSION_MAX_INACTIVE_INTERVAL_MIN),
    ACCOUNT_ACTIVATION(ACCOUNT_ACTIVATION_SESSION_TYPE, ACCOUNT_ACTIVATION_SESSION_MAX_INACTIVE_INTERVAL_MIN),
    PASSWORD_RESET(PASSWORD_RESET_SESSION_TYPE, PASSWORD_RESET_SESSION_MAX_INACTIVE_INTERVAL_MIN);

    private final String sessionType;
    private final long sessionMaxInactiveIntervalMin;

    private SessionTypeEnum(String sessionType, long sessionMaxInactiveIntervalMin) {
        this.sessionType = sessionType;
        this.sessionMaxInactiveIntervalMin = sessionMaxInactiveIntervalMin;
    }

    public String getSessionType() {
        return this.sessionType;
    }

    public long getSessionMaxInactiveIntervalMin() {
        return this.sessionMaxInactiveIntervalMin;
    }

    public abstract static class SessionTypeConstants {

        public static final String ACCESS_SESSION_TYPE                                  = "ACCESS";
        public static final long ACCESS_SESSION_MAX_INACTIVE_INTERVAL_MIN               = 480;

        public static final String ACCOUNT_ACTIVATION_SESSION_TYPE                      = "ACCOUNT_ACTIVATION";
        public static final long ACCOUNT_ACTIVATION_SESSION_MAX_INACTIVE_INTERVAL_MIN   = 120;

        public static final String PASSWORD_RESET_SESSION_TYPE                          = "PASSWORD_RESET";
        public static final long PASSWORD_RESET_SESSION_MAX_INACTIVE_INTERVAL_MIN       = 5;

        private SessionTypeConstants() {

        }

    }

}
