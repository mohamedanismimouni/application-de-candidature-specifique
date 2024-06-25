package com.talan.polaris.enumerations;

import static com.talan.polaris.enumerations.SkillTypeEnum.SkillTypeConstants.*;

/**
 * SkillTypeEnum.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public enum SkillTypeEnum {

    HUMAN(HUMAN_SKILL_TYPE),
    TECHNICAL(TECHNICAL_SKILL_TYPE);

    private final String skillType;

    private SkillTypeEnum(String skillType) {
        this.skillType = skillType;
    }

    public String getSkillType() {
        return this.skillType;
    }

    public abstract static class SkillTypeConstants {

        public static final String HUMAN_SKILL_TYPE            = "HUMAN";
        public static final String TECHNICAL_SKILL_TYPE        = "TECHNICAL";

        private SkillTypeConstants() {

        }

    }

}
