package com.talan.polaris.enumerations;


import static com.talan.polaris.enumerations.EventStatusEnum.EventStatusConstants.ACTIVE_STATUS;
import static com.talan.polaris.enumerations.EventStatusEnum.EventStatusConstants.ARCHIVED_STATUS;

public enum EventStatusEnum {

   ACTIVE(ACTIVE_STATUS),
    ARCHIVED(ARCHIVED_STATUS);

    private final String eventStatus;

    private EventStatusEnum(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getEventStatus() {
        return this.eventStatus;
    }

    public abstract static class EventStatusConstants {

        public static final String ACTIVE_STATUS              = "ACTIVE";
        public static final String ARCHIVED_STATUS            = "ARCHIVED";


        private EventStatusConstants() {

        }

    }

}
