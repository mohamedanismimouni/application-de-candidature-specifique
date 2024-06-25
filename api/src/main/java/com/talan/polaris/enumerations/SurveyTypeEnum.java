package com.talan.polaris.enumerations;

import static com.talan.polaris.enumerations.SurveyTypeEnum.SurveyTypeConstants.*;

/**
 * SurveyTypeEnum.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public enum SurveyTypeEnum {

    AWARENESS(AWARENESS_SURVEY_TYPE),
    EXPLORATION(EXPLORATION_SURVEY_TYPE),
    EVALUATION(EVALUATION_SURVEY_TYPE);

    private final String surveyType;

    private SurveyTypeEnum(String surveyType) {
        this.surveyType = surveyType;
    }

    public String getSurveyType() {
        return this.surveyType;
    }

    public abstract static class SurveyTypeConstants {

        public static final String AWARENESS_SURVEY_TYPE          = "AWARENESS";
        public static final String EXPLORATION_SURVEY_TYPE        = "EXPLORATION";
        public static final String EVALUATION_SURVEY_TYPE         = "EVALUATION";

        private SurveyTypeConstants() {

        }

    }

}
