package com.talan.polaris.enumerations;

import static com.talan.polaris.enumerations.EvaluationStatusEnum.EvaluationStatusConstants.*;

/**
 * EvaluationStatusEnum.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public enum EvaluationStatusEnum {

    OPEN(OPEN_EVALUATION_STATUS),
    CLOSED(CLOSED_EVALUATION_STATUS);

    private final String evaluationStatus;

    private EvaluationStatusEnum(String evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    public String getEvaluationStatus() {
        return this.evaluationStatus;
    }

    public abstract static class EvaluationStatusConstants {

        public static final String OPEN_EVALUATION_STATUS             = "OPEN";
        public static final String CLOSED_EVALUATION_STATUS           = "CLOSED";

        private EvaluationStatusConstants() {

        }

    }

}
