export const API_DOMAIN = (window as any).api.domain;
export const KEYCLOAK_HOST = (window as any).api.keycloak;
export const VERSION_MAJOR = (window as any).api.version.major;
export const VERSION_MINOR =(window as any).api.version.minor;
export const VERSION_PATCH =(window as any).api.version.patch;

export const API_AUTHENTICATION_ENDPOINTS_PREFIX = '/auth';
export const API_AUTHENTICATION_BASE_URL = API_DOMAIN + API_AUTHENTICATION_ENDPOINTS_PREFIX;

export const API_AUTHENTICATION_PATHS = {
    SIGN_UP_PATH: '/sign-up',
    SIGN_IN_PATH: '/sign-in',
    SIGN_OUT_PATH: '/sign-out'

};

export const API_RESOURCES_ENDPOINTS_PREFIX = '/api/v' + (window as any).api.version.major;

export const API_RESOURCES_BASE_URL = API_DOMAIN + API_RESOURCES_ENDPOINTS_PREFIX;

export const API_RESOURCES_PATHS = {

    USERS_RESOURCE_PATHS: {
        BASE_PATH: '/users',
        CURRENT_USER_PATH: '/users/current',
        TEAMS_ASSIGNMENT_STATISTICS_PATH: '/users/teams-assignment-statistics',
        ACCOUNT_ACTIVATION_PATH: '/users/account-activation',
        ACCOUNT_ACTIVATION_MAIL_PATH: '/users/account-activation-mail',
        PASSWORD_RESET_MAIL_PATH: '/users/password-reset-mail',
        PASSWORD_RESET_PATH: '/users/password-reset',
        ACCOUNT_STATUS_UPDATE_PATH: '/users/account-status',
        MEMBER_OF_PATH: '/member-of',
        SECRET_WORD_PATH: '/secret-word',
        UPDATE_FUNCTION: '/users/updateFunction',
        USER_TEAM_IFORMATION_PATH:'/users/team'
    },
    TEAMS_RESOURCE_PATHS: {
        BASE_PATH: '/teams'
    },
    CAREER_STEPS_RESOURCE_PATHS: {
        BASE_PATH: '/career-steps'
    },
    CAREER_PATHS_RESOURCE_PATHS: {
        BASE_PATH: '/career-paths'
    },
    QUESTIONS_RESOURCE_PATHS: {
        BASE_PATH: '/questions',
        CHOICES_PATH: '/choices'
    },
    RESPONSES_RESOURCE_PATHS: {
        BASE_PATH: '/responses'
    },
    SKILLS_RESOURCE_PATHS: {
        BASE_PATH: '/skills'
    },
    PROFILES_RESOURCE_PATHS: {
        BASE_PATH: '/profiles'
    },
    REQUIRED_SKILLS_RESOURCE_PATHS: {
        BASE_PATH: '/required-skills'
    },
    CAREER_POSITIONS_RESOURCE_PATHS: {
        BASE_PATH: '/career-positions',
        UPDATE_PATH: '/career-positions/update'

    },
    ACQUIRED_SKILLS_RESOURCE_PATHS: {
        BASE_PATH: '/acquired-skills',
        PROGRESS_PATH: '/progress'
    },
    MENTORSHIPS_RESOURCE_PATHS: {
        BASE_PATH: '/mentorships',
        MENTOR_EVALUATION_PATH: '/mentor-evaluation',
        MENTEE_EVALUATION_PATH: '/mentee-evaluation'
    },
    ONBOARDINGS_RESOURCE_PATHS: {
        BASE_PATH: '/onboardings',
        RATING_PATH: '/rating',
        ONBOARDING_HINT_PATH: '/onboardings/hint'
    },
    EVALUATIONS_RESOURCE_PATHS: {
        BASE_PATH: '/evaluations'
    },
    DOCUMENT_REQUEST_PATHS: {
        BASE_PATH: '/requests',
        IN_WAITING_REQUEST_PATH: '/requests/inWaiting',
        PROCESSED_REQUEST_PATH: '/requests/processed',
        CANCEL_REQUEST_PATH: '/requests/cancel',
        REJECTION_REQUEST_PATH: '/requests/rejection',
        VALIDATE_REQUEST_PATH : '/requests/validate',
        VALIDATE_ALL_REQUEST_PATH : '/requests/validateAll',
        DOWNLOAD_ETIQUETTE_MERGED_PATH : '/requests/validateAll/download',
        DELETE_ETIQUETTE_MERGED_PATH : '/requests/validateAll/deleteFile',
        REQUESTS_WITHOUT_TEMPLAT:'/requests/withoutTemplate',
        UPDATE_REQUEST_PATH :'/requests/received',
    }
    , REQUESTS_TYPE_RESOURCE_PATHS: {
        BASE_PATH: '/requestsTypes',
        GET_TEMPLATE_REQUESTS: '/requestsTypes/getTemplates',
        UPDATE_REQUEST_TYPE: '/requestsTypes/updateRequestType',
        LAST_UPLOAD_PAYROLL:'/requestsTypes/lastUploadPayRoll',
        LAST_UPLOAD_REMINDER:'/requestsTypes/lastUploadReminder',
        GET_TYPE_BY_LABEL:'/requestsTypes/getTypeByLabel'
        },
    EDM_FILE_PATHS: {
        BASE_PATH:'/edm',
        UPLOAD_SINGLE_FILE:'/edm/upload',
        DOWNLOAD_SINGLE_FILE:'/edm/download',
        DELDETE_SINGLE_FILE:'/edm/delete'
    },
    PAYROLL_PATHS: {
        BASE_PATH:'payRoll',
        UPLOAD_PAYROLL_FILE:'/payRoll/uploadPayroll',
        UPLOAD_REMINDER_FILE:'/payRoll/uploadReminder',
    }
    ,
    COLLAB_PATHS: {
        BASE_PATH:'/collab',
        SYNCHRO_PATH:'/collab/synchronizeCollab',
        PATH_COLLAB_WITH_USER:'/collab/collaboratorsUser'


    },
    FUNCTION_PATHS: {
        BASE_PATH:'/functions'
    },
    EVENTS_RESOURCE_PATHS: {

        BASE_PATH: '/event',
        UPCOMING_EVENTS_PATH: '/event/upcoming',
        PAST_EVENTS_PATH: '/event/past',
        PARTICIPATE_EVENTS_PATH: '/event/participate',
        CANCEL_EVENTS_PATH: '/event/cancel',
        DELETE_Event_PATH:'/event/deleteEvent',
        UPDATE_EVENT: '/event/update-event'

    },

    QUIZ_PATHS:{
        BASE_PATH :'/quiz'
    },
    USEFUL_DOCUMENT_PATHS: {
        BASE_PATH: '/usefulDocuments',
        UPDATE_USEFUL_DOCUMENT:'/usefulDocuments/updateUsefulDocument',
        GENERATE_IMAGE: '/usefulDocuments/generatePDFToImage',
        DELETE_IMAGE: '/usefulDocuments/deleteImage',
        GET_IMAGES: '/usefulDocuments/getImages'
        }
        ,
    USEFUL_LINK_PATHS:{BASE_PATH:'/usefulLink'},
    PROVERB_PATHS:{
        BASE_PATH:'/proverb',
        PUBLISHED_PROVERB_PATH:'/proverb/publishedProverb',
        EXIST_PROVERB_DAY:'/proverb/existProverbOfDay',
        ADD_PROVERB_PATH:'/proverb/addProverb',
        GET_PROVERB_SCORE_PATH:'/proverb/scoreProverb',
        DELETE_PROVERB_PATH:'/proverb/deleteProverb'

    },
    MOOD_COLLAB: {
        BASE_PATH: '/mood-paths',
        ACTUAL_MOOD: '/mood-paths/actual-mood',
        UPDATE_MOOD: '/mood-paths/update-mood',
        CURRENT_MOOD: '/mood-paths/current-mood',
    },
    SCORE:{
        BASE_PATH: '/score',
    },
    KPI_COLLAB_PATH:{
        BASE_PATH: '/requests/KPIDocument'
    }

};





