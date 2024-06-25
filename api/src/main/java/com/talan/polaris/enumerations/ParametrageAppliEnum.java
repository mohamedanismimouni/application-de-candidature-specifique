package com.talan.polaris.enumerations;
import static com.talan.polaris.enumerations.ParametrageAppliEnum.ParametrageAppliConstants.DUREE_AUTO_VALIDATION_DEMANDE_ADMIN_PARAM;
/**
 * AccountStatusEnum.
 *
 * @author Imen Mechergui
 * @since 1.1.0
 */
public enum ParametrageAppliEnum {

    DUREE_AUTO_VALIDATION_DEMANDE_ADMIN(DUREE_AUTO_VALIDATION_DEMANDE_ADMIN_PARAM);


    private final String param;

    private ParametrageAppliEnum(String param) {
        this.param = param;
    }

    public String getParam() {
        return this.param;
    }

    public abstract static class ParametrageAppliConstants {

        public static final String  DUREE_AUTO_VALIDATION_DEMANDE_ADMIN_PARAM="DureeAutoValidDemandeAdmin";


        private ParametrageAppliConstants() {

        }

    }

}
