package com.talan.polaris.enumerations;

import static com.talan.polaris.enumerations.MentorshipStatusEnum.MentorshipStatusConstants.*;

/**
 * MentorshipStatusEnum.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public enum MentorshipStatusEnum {

    ACTIVE(ACTIVE_MENTORSHIP_STATUS),
    TERMINATED(TERMINATED_MENTORSHIP_STATUS);

    private final String mentorshipStatus;

    private MentorshipStatusEnum(String mentorshipStatus) {
        this.mentorshipStatus = mentorshipStatus;
    }

    public String getMentorshipStatus() {
        return this.mentorshipStatus;
    }

    public abstract static class MentorshipStatusConstants {

        public static final String ACTIVE_MENTORSHIP_STATUS           = "ACTIVE";
        public static final String TERMINATED_MENTORSHIP_STATUS       = "TERMINATED";

        private MentorshipStatusConstants() {

        }

    }

}
