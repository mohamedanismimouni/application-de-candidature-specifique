package com.talan.polaris.enumerations;

import java.math.BigDecimal;

import static com.talan.polaris.enumerations.SkillLevelEnum.SkillLevelConstants.*;

/**
 * SkillLevelEnum.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public enum SkillLevelEnum {

    BEGINNER(BEGINNER_SKILL_LEVEL),
    INTERMEDIATE(INTERMEDIATE_SKILL_LEVEL),
    ADVANCED(ADVANCED_SKILL_LEVEL),
    EXPERT(EXPERT_SKILL_LEVEL);

    private final BigDecimal skillLevel;

    private SkillLevelEnum(BigDecimal skillLevel) {
        this.skillLevel = skillLevel;
    }

    public BigDecimal getSkillLevel() {
        return this.skillLevel;
    }

    public abstract static class SkillLevelConstants {

        public static final BigDecimal BEGINNER_SKILL_LEVEL        = BigDecimal.valueOf(0.25);
        public static final BigDecimal INTERMEDIATE_SKILL_LEVEL    = BigDecimal.valueOf(0.5);
        public static final BigDecimal ADVANCED_SKILL_LEVEL        = BigDecimal.valueOf(0.75);
        public static final BigDecimal EXPERT_SKILL_LEVEL          = BigDecimal.valueOf(1);

        private SkillLevelConstants() {

        }

    }

}
