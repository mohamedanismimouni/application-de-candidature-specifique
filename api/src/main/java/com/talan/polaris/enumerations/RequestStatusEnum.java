package com.talan.polaris.enumerations;

import static com.talan.polaris.enumerations.RequestStatusEnum.RequestStatusConstants.*;

public enum RequestStatusEnum {

    IN_WAITING(IN_WAITING_STATUS_TYPE),
    VALIDATED(VALIDATED_STATUS_TYPE),
    INVALIDATED(INVALIDATED_STATUS_TYPE ),
    CANCELED(CANCELED_STATUS_TYPE),
    RECEIVED(RECEIVED_STATUS_TYPE);

    private final String requestStatus;

    private RequestStatusEnum(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getProfileType() {
        return this.requestStatus;
    }

    public abstract static class RequestStatusConstants {

        public static final String IN_WAITING_STATUS_TYPE            = "IN_WAITING";
        public static final String VALIDATED_STATUS_TYPE             = "VALIDATED";
        public static final String INVALIDATED_STATUS_TYPE           = "INVALIDATED";
        public static final String CANCELED_STATUS_TYPE              = "CANCELED";
        public static final String RECEIVED_STATUS_TYPE              = "RECEIVED";


        private RequestStatusConstants() {

        }

    }

}
