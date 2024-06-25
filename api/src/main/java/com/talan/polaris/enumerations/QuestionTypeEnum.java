package com.talan.polaris.enumerations;

import static com.talan.polaris.enumerations.QuestionTypeEnum.QuestionTypeConstants.*;

/**
 * QuestionTypeEnum.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public enum QuestionTypeEnum {

    OPEN_ENDED(OPEN_ENDED_QUESTION_TYPE),
    RATING_SCALE(RATING_SCALE_QUESTION_TYPE),
    REGULAR_CHOICE(REGULAR_CHOICE_QUESTION_TYPE),
    QUIZ_CHOICE(QUIZ_CHOICE_QUESTION_TYPE);

    private final String questionType;

    private QuestionTypeEnum(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestionType() {
        return this.questionType;
    }

    public abstract static class QuestionTypeConstants {

        public static final String OPEN_ENDED_QUESTION_TYPE         = "OPEN_ENDED";
        public static final String RATING_SCALE_QUESTION_TYPE       = "RATING_SCALE";
        public static final String REGULAR_CHOICE_QUESTION_TYPE     = "REGULAR_CHOICE";
        public static final String QUIZ_CHOICE_QUESTION_TYPE        = "QUIZ_CHOICE";

        private QuestionTypeConstants() {

        }

    }

}
