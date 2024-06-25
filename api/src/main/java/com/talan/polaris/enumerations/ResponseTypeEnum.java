package com.talan.polaris.enumerations;

import static com.talan.polaris.enumerations.ResponseTypeEnum.ResponseTypeConstants.*;

/**
 * ResponseTypeEnum.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public enum ResponseTypeEnum {

    OPEN_ENDED(OPEN_ENDED_RESPONSE_TYPE),
    RATING_SCALE(RATING_SCALE_RESPONSE_TYPE),
    REGULAR_CHOICE(REGULAR_CHOICE_RESPONSE_TYPE),
    QUIZ_CHOICE(QUIZ_CHOICE_RESPONSE_TYPE);

    private final String responseType;

    private ResponseTypeEnum(String responseType) {
        this.responseType = responseType;
    }

    public String getResponseType() {
        return this.responseType;
    }

    public abstract static class ResponseTypeConstants {

        public static final String OPEN_ENDED_RESPONSE_TYPE         = "OPEN_ENDED";
        public static final String RATING_SCALE_RESPONSE_TYPE       = "RATING_SCALE";
        public static final String REGULAR_CHOICE_RESPONSE_TYPE     = "REGULAR_CHOICE";
        public static final String QUIZ_CHOICE_RESPONSE_TYPE        = "QUIZ_CHOICE";

        private ResponseTypeConstants() {

        }

    }

}
