package com.talan.polaris.constants;

/**
 * ConfigurationConstants.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public abstract class ConfigurationConstants {

    public static final String CONFIGURATION_PROPERTIES_FILE_BASENAME   = "configuration";
    public static final String CONFIGURATION_PROPERTIES_FILE_NAME       = "configuration.properties";

    public static final String API_VERSION                              = "configuration.api.version";
    public static final String API_ENDPOINTS_AUTHENTICATION_PREFIX      = "configuration.api.endpoints.authentication.prefix";
    public static final String API_ENDPOINTS_RESOURCES_PREFIX           = "configuration.api.endpoints.resources.prefix";
    public static final String MANAGEMENT_ENDPOINTS_PREFIX              = "management.endpoints.web.base-path";

    public static final String SECURITY_CORS_ALLOWED_ORIGINS            = "configuration.security.cors.allowed-origins";
    public static final String SECURITY_PASSWORD_INITIALIZATION_LINK    = "configuration.security.password-initialization-link";

    public static final String MAIL_TEMPLATE_PATH                       = "configuration.mail.template.path";
    public static final String MAIL_QUIZ_LINK_TEMPLATE_PATH             = "configuration.mail.quiz-link.template.path";
    public static final String USER_MANAGEMENT_LINK                     = "configuration.security.user-management";
    public static final String DOCUMENT_REQUEST_LINK                    = "configuration.security.document-request-link";
    public static final String DOCUMENT_RESPONSE_LINK                   = "configuration.security.document-response-link";
    public static final String EVENT_LINK                               ="configuration.security.event-link";
    public static final String EDM_BASE_PATH                            = "configuration.edm.base-link";
    public static final String EDM_UPLOAD_SINGLE_FILE_PATH               = "configuration.edm.upload-single-file-link";
    public static final String EDM_DOWNLOAD_FILE_PATH                    = "configuration.edm.download-single-file-link";
    public static final String EDM_DELETE_FILE_PATH                      = "configuration.edm.delete-file-link";
    public static final String  LOGIN_EDM                                = "configuration.edm.login" ;
    public static final String  PASSWORD_EDM                             = "configuration.edm.password" ;
    public static final String BYBLOS_BASE_PATH                          = "configuration.byblos.base.link";
    public static final String  INI_SALA_CRON                            = "configuration.SalaryInitiation.job" ;
    public static final String BYBLOS_LOGIN_PATH                         = "configuration.byblos.login.link";
    public static final String BYBLOS_GET_COLLAB_PATH                    = "configuration.byblos.get.collab.link";
    public static final String BYBLOS_USER_LOG_IN                        = "configuration.byblos.user.log.in";
    public static final String BYBLOS_USER_PWD                           = "configuration.byblos.user.pwd";
    public static final String LOAD_COLLAB_CRON                           ="configuration.byblos.load.collab.job";
    public static final String VALIDATE_REQUESTS_CRON                    = "configuration.ValidateRequests.job" ;
    public static final String KEYCLOAK_GET_CLIENT_ID                    = "configuration.keycloak.get-client-id";
    public static final String KEYCLOAK_GET_USERS_BY_CLIENT_ROLE         = "configuration.keycloak.get-users-by-client-role";
    public static final String KEYCLOAK_GET_ACCESS_TOKEN        		 = "configuration.keycloak.get-access-token";
    public static final String KEYCLOAK_USERNAME        		 		 = "configuration.keycloak.username";
    public static final String KEYCLOAK_PASSWORD        		 		 = "configuration.keycloak.password";
    public static final String KEYCLOAK_FRONT_CLIENT        			 = "configuration.keycloak.front-client";
    public static final String KEYCLOAK_BACK_CLIENT        			 	 = "configuration.keycloak.back-client";
    public static final String ARCHIVE_PAST_EVENT_CRON        			 = "configuration.ArchivePastEvent.job";
    public static final String PROVERB_CRON                  			 = "configuration.proverb.job";




    private ConfigurationConstants() {

    }

}
