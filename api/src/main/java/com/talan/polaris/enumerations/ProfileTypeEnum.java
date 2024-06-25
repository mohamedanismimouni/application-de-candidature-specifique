package com.talan.polaris.enumerations;

import static com.talan.polaris.enumerations.ProfileTypeEnum.ProfileTypeConstants.*;

/**
 * ProfileTypeEnum.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public enum ProfileTypeEnum {

    DIRECTOR(DIRECTOR_PROFILE_TYPE),
    HR_RESPONSIBLE(HR_RESPONSIBLE_PROFILE_TYPE),
    MANAGER(MANAGER_PROFILE_TYPE),
    COLLABORATOR(COLLABORATOR_PROFILE_TYPE),
    SUPPORT(SUPPORT_PROFILE_TYPE);

    private final String profileType;

    private ProfileTypeEnum(String profileType) {
        this.profileType = profileType;
    }
    
    public String getProfileType() {
        return this.profileType;
    }

    public abstract static class ProfileTypeConstants {

        public static final String DIRECTOR_PROFILE_TYPE            = "DIRECTOR";
        public static final String HR_RESPONSIBLE_PROFILE_TYPE      = "HR_RESPONSIBLE";
        public static final String MANAGER_PROFILE_TYPE             = "MANAGER";
        public static final String COLLABORATOR_PROFILE_TYPE        = "COLLABORATOR";
        public static final String SUPPORT_PROFILE_TYPE             = "SUPPORT_ADMIN";


        private ProfileTypeConstants() {

        }

    }

}
