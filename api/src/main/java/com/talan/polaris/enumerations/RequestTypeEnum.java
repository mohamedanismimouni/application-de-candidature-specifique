package com.talan.polaris.enumerations;

import static com.talan.polaris.enumerations.RequestTypeEnum.RequestTypeConstants.*;

public enum RequestTypeEnum {
    WORK_CERTIFICATE(WORK_CERTIFICATE_REQUEST_TYPE),
    MISSION_CERTIFICATE(MISSION_CERTIFICATE_REQUEST_TYPE),
    SALARY_CERTIFICATE(SALARY_CERTIFICATE_REQUEST_TYPE),
    PAYROLL(PAYROLL_REQUEST_TYPE),
    USER_GUIDE(USER_GUIDE_TYPE),
    ETIQUETTE_TYPE(ETIQUETTE_REQUEST_TYPE),
    REMINDER(REMINDER_TYPE);

    private final String requestType;

    private RequestTypeEnum(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestType() {
        return this.requestType;
    }

    public abstract static class RequestTypeConstants {

        public static final String WORK_CERTIFICATE_REQUEST_TYPE           = "WORK_CERTIFICATE";
        public static final String MISSION_CERTIFICATE_REQUEST_TYPE        = "MISSION_CERTIFICATE";
        public static final String SALARY_CERTIFICATE_REQUEST_TYPE         = "SALARY_CERTIFICATE";
        public static final String PAYROLL_REQUEST_TYPE                    = "PAYROLL";
        public static final String REMINDER_TYPE                           = "REMINDER";
        public static final String ETIQUETTE_REQUEST_TYPE                  = "ETIQUETTE_TYPE";
        public static final String USER_GUIDE_TYPE                         = "USER_GUIDE";
        private RequestTypeConstants() {

        }

    }

}
