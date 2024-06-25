package com.talan.polaris.constants;
import java.util.Locale;
/**
 * CommonConstants.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
public abstract class CommonConstants {

    public static final String ENCODING_UTF8                                = "UTF-8";
    public static final String ENCODING_ASC                                 = "US-ASCII";


    public static final Locale LOCALE_DEFAULT                               = Locale.ENGLISH;

    public static final String PATTERN_EMAIL                                = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String PATTERN_PASSWORD                             = "^.*(?=.{8,})((?=.*[!@#$%^&*()\\-_=+{};:,<.>]){1})(?=.*\\d)((?=.*[a-z]){1})((?=.*[A-Z]){1}).*$";

    public static final String SESSION_ATTRIBUTE_SESSION_TYPE               = "SESSION_TYPE";

    public static final String SQL_STATE_FOREIGN_KEY_INTEGRITY_VIOLATION    = "23503";
    public static final String SQL_STATE_UNIQUE_CONSTRAINT_VIOLATION        = "23505";

    public static final int COLLABORATOR_PROBATIONARY_PERIOD_IN_MONTHS      = 6;

    public static final int ONBOARDING_RATING_MIN                           = -2;
    public static final int ONBOARDING_RATING_MAX                           = 2;

    public static final int MENTORSHIP_RATING_MIN                           = -2;
    public static final int MENTORSHIP_RATING_MAX                           = 2;
    public static final int MENTORSHIP_FEEDBACK_MAX_LENGTH                  = 250;

    public static final int OPEN_ENDED_RESPONSE_CONTENT_MAX_LENGTH          = 500;

    public static final int EVALUATION_RATING_MIN                           = -2;
    public static final int EVALUATION_RATING_MAX                           = 2;
    public static final int EVALUATION_FEEDBACK_MAX_LENGTH                  = 250;
    public static final String TIMESTAMP_PATTERN                            ="dd MMMM YYYY";
    public static final String JOB_STATUS_OK                                ="OK";
    public static final String JOB_STATUS_KO                                ="KO";
    public static final Double ZERO_VALUE                                   =0.0;
    public static final String REFERENCE_VAR                                ="${reference}";
    public static final String WORK_CERTIFICATE                             ="Work certificate";
    public static final String SALARY_CERTIFICATE                           ="Salary certificate";
    public static final String FEMININ_E                                    ="e";
    public static final String NUMERIC_TYPE                                 ="NUMERIC";
    public static final String ABRV_CIVILITY_MR                             ="Mr";
    public static final String ABRV_CIVILITY_MME                            ="Mme";
    public static final String INITIALIZE_TEAM                              ="A definir";
    public static final String INITIALIZE_PROFILE                           ="A definir";
    public static final String INITIALIZE_USER_PWD                          ="Aa@123456789";
    public static final String INITIALIZE_SUPERVISOR_EMAIL                  ="Adefinir@gmail.com";
    public static final String TRIGGER_GROUP_NAME                           ="TG_GROUP";
    public static final String JOB_DETAILS_GROUP_NAME                       ="MS_GROUP";
    public static final String AUTO_VALIDATION_JOB_NAME                     ="AutoValidation Job";
    public static final String AUTO_VALIDATION_TRIGGER_NAME                 ="AutoValidation Trigger";
    public static final String AUTO_VALIDATION_JOB_BEAN_NAME                ="autovalidationJob";
    public static final String AUTO_VALIDATION_TRIGGER_BEAN_NAME            ="autovalidationTrigger";
    public static final String JOB_AUTO_VALIDATION_DESCRIPTION              ="validate in waiting requests by system, after 24 hours of their creations";
    public static final String TRIGGER_AUTO_VALIDATION_DESCRIPTION          ="AutoValidation Job start at 00 AM every day";
    public static final String TIME_ZONE_PARIS                              ="Europe/Paris";
    public static final String ETIQUETTE_FILE_NAME                          ="Etiquette.Docx";
    public static final String QRCODE_IMAGE_NAME                            ="QRcode";
    public static final String QRCODE_IMAGE_TYPE                            =".png";
    public static final int QRCODE_IMAGE_WIDTH                              = 100;
    public static final int QRCODE_IMAGE_HEIGHT                             = 100;
    public static final String QRCODE_TEXT                                  ="Your request is validated";
    public static final String PASS_PHRASE                                  ="AzertyUI#2020";
    public static final String PROVERB_COMPONENT_LABEL                      ="proverb";
    public static final String EVENT_COMPONENT_LABEL                        ="event";
    public static final String QUIZ_COMPONENT_LABEL                         ="quiz";
    public static final String PARTICIPATE_EVENT_COMPONENT_LABEL            ="participate event";
    public static final String EVENT_SIRIUS_MAIL                            ="Event Sirius HR :";
    public static final String EVENT_MAIL_BODY                              ="Hello, It is with great pleasure that we announce the holding of the event entitled ";
    public static final String EVENT_MAIL_SUPPORT                           ="Sirius HR Support";






    private CommonConstants() {

    }

}
