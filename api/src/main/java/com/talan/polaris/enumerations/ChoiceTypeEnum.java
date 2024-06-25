package com.talan.polaris.enumerations;

import static com.talan.polaris.enumerations.ChoiceTypeEnum.ChoiceTypeConstants.*;

/**
 * ChoiceTypeEnum.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public enum ChoiceTypeEnum {

    REGULAR(REGULAR_CHOICE_TYPE),
    QUIZ(QUIZ_CHOICE_TYPE);

    private final String choiceType;

    private ChoiceTypeEnum(String choiceType) {
        this.choiceType = choiceType;
    }

    public String getChoiceType() {
        return this.choiceType;
    }

    public abstract static class ChoiceTypeConstants {

        public static final String REGULAR_CHOICE_TYPE      = "REGULAR";
        public static final String QUIZ_CHOICE_TYPE         = "QUIZ";

        private ChoiceTypeConstants() {

        }

    }

}
