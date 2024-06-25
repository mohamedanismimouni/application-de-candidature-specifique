package com.talan.polaris.enumerations;

import java.math.BigDecimal;

import static com.talan.polaris.enumerations.SkillWeightEnum.SkillWeightConstants.*;

/**
 * SkillWeightEnum.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public enum SkillWeightEnum {

    LOW(LOW_SKILL_WEIGHT),
    MODERATE(MODERATE_SKILL_WEIGHT),
    HIGH(HIGH_SKILL_WEIGHT),
    CRITICAL(CRITICAL_SKILL_WEIGHT);

    private final BigDecimal skillWeight;

    private SkillWeightEnum(BigDecimal skillWeight) {
        this.skillWeight = skillWeight;
    }

    public BigDecimal getSkillWeight() {
        return this.skillWeight;
    }

    public abstract static class SkillWeightConstants {

        public static final BigDecimal LOW_SKILL_WEIGHT         = BigDecimal.valueOf(0.25);
        public static final BigDecimal MODERATE_SKILL_WEIGHT    = BigDecimal.valueOf(0.5);
        public static final BigDecimal HIGH_SKILL_WEIGHT        = BigDecimal.valueOf(0.75);
        public static final BigDecimal CRITICAL_SKILL_WEIGHT    = BigDecimal.valueOf(1);

        private SkillWeightConstants() {

        }

    }

}
