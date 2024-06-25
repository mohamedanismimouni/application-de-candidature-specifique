package com.talan.polaris.enumerations;

import static com.talan.polaris.enumerations.CareerPositionStatusEnum.CareerPositionStatusConstants.*;

/**
 * CareerPositionStatusEnum.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public enum CareerPositionStatusEnum {

    PREVIOUS(PREVIOUS_CAREER_POSITION_STATUS),
    CURRENT(CURRENT_CAREER_POSITION_STATUS),
    NEXT(NEXT_CAREER_POSITION_STATUS);

    private final String careerPositionStatus;

    private CareerPositionStatusEnum(String careerPositionStatus) {
        this.careerPositionStatus = careerPositionStatus;
    }

    public String getCareerPositionStatus() {
        return this.careerPositionStatus;
    }

    public abstract static class CareerPositionStatusConstants {

        public static final String PREVIOUS_CAREER_POSITION_STATUS      = "PREVIOUS";
        public static final String CURRENT_CAREER_POSITION_STATUS       = "CURRENT";
        public static final String NEXT_CAREER_POSITION_STATUS          = "NEXT";

        private CareerPositionStatusConstants() {

        }

    }

}
