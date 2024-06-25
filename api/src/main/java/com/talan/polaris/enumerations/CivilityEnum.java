package com.talan.polaris.enumerations;

import static com.talan.polaris.enumerations.CivilityEnum.CivilityConstants.FEMALE_CIVILTY;
import static com.talan.polaris.enumerations.CivilityEnum.CivilityConstants.MALE_CIVILITY;

public enum CivilityEnum {

    FEMALE(FEMALE_CIVILTY),
    MALE(MALE_CIVILITY);


    private final String civility;

    private CivilityEnum(String civility) {
        this.civility = civility;
    }

    public String getCivilty() {
        return this.civility;
    }

    public abstract static class CivilityConstants {

        public static final String FEMALE_CIVILTY = "Madame";
        public static final String MALE_CIVILITY = "Monsieur";


        private CivilityConstants() {

        }

    }

}
