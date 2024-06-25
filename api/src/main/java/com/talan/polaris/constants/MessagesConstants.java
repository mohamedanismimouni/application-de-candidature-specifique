package com.talan.polaris.constants;

/**
 * MessagesConstants.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public abstract class MessagesConstants {

    public static final String MESSAGES_PROPERTIES_FILE_BASENAME                                                        = "messages";
    public static final String MESSAGES_PROPERTIES_FILE_NAME                                                            = "messages.properties";

    public static final String ERROR_BAD_REQUEST                                                                        = "messages.error.bad-request";
    public static final String ERROR_BAD_REQUEST_DATA_INTEGRITY_VIOLATION                                               = "messages.error.bad-request.data-integrity-violation";
    public static final String ERROR_BAD_REQUEST_UNIQUE_CONSTRAINT_VIOLATION                                            = "messages.error.bad-request.unique-constraint-violation";
    public static final String ERROR_BAD_REQUEST_FOREIGN_KEY_INTEGRITY_VIOLATION                                        = "messages.error.bad-request.foreign-key-integrity-violation";
    public static final String ERROR_BAD_REQUEST_VALIDATION_CONSTRAINTS_VIOLATION                                       = "messages.error.bad-request.validation-constraints-violation";
    public static final String ERROR_BAD_REQUEST_VALIDATION_CONSTRAINTS_VIOLATION_EMAIL_CONSTRAINT                      = "messages.error.bad-request.validation-constraints-violation.email-constraint";
    public static final String ERROR_BAD_REQUEST_VALIDATION_CONSTRAINTS_VIOLATION_PASSWORD_CONSTRAINT                   = "messages.error.bad-request.validation-constraints-violation.password-constraint";
    public static final String ERROR_BAD_REQUEST_VALIDATION_CONSTRAINTS_VIOLATION_PASSWORD_CONFIRMATION_CONSTRAINT      = "messages.error.bad-request.validation-constraints-violation.password-confirmation-constraint";
    public static final String ERROR_BAD_REQUEST_VALIDATION_CONSTRAINTS_VIOLATION_SURVEY_QUESTION_CONSTRAINT            = "messages.error.bad-request.validation-constraints-violation.survey-question-constraint";
    public static final String ERROR_BAD_REQUEST_VALIDATION_CONSTRAINTS_VIOLATION_SCALE_CONSTRAINT                      = "messages.error.bad-request.validation-constraints-violation.scale-constraint";
    public static final String ERROR_BAD_REQUEST_VALIDATION_CONSTRAINTS_VIOLATION_QUESTION_CHOICE_CONSTRAINT            = "messages.error.bad-request.validation-constraints-violation.question-choice-constraint";
    public static final String ERROR_BAD_REQUEST_VALIDATION_CONSTRAINTS_VIOLATION_CURRENT_CAREER_POSITION_CONSTRAINT    = "messages.error.bad-request.validation-constraints-violation.current-career-position-constraint";
    public static final String ERROR_BAD_REQUEST_INVALID_FORMAT_JSON_PATCH_PARSING                                      = "messages.error.bad-request.invalid-format.json-patch-parsing";
    public static final String ERROR_BAD_REQUEST_JSON_PATCH                                                             = "messages.error.bad-request.json-patch";
    public static final String ERROR_BAD_REQUEST_CAREER_PATH_CREATION                                                   = "messages.error.bad-request.career-path-creation";
    public static final String ERROR_BAD_REQUEST_QUESTION_CHOICE_SUPPORT                                                = "messages.error.bad-request.question-choice-support";
    public static final String ERROR_BAD_REQUEST_QUESTION_MIN_CHOICES                                                   = "messages.error.bad-request.question-min-choices";
    public static final String ERROR_BAD_REQUEST_PROFILES_CHAINING                                                      = "messages.error.bad-request.profiles-chaining";
    public static final String ERROR_BAD_REQUEST_CAREER_POSITION_INITIALIZED_TEAM_PROFILE_CONSTRAINT                    = "messages.error.bad-request.career-position.initialized-team-profile-constraint";
    public static final String ERROR_BAD_REQUEST_CAREER_POSITION_CAREER_POSITION_STATUSES_CONSTRAINT                    = "messages.error.bad-request.career-position.career-position-statuses-constraint";
    public static final String ERROR_BAD_REQUEST_CAREER_POSITION_SUPERVISOR_CONSTRAINT                                  = "messages.error.bad-request.career-position.supervisor-constraint";
    public static final String ERROR_BAD_REQUEST_CAREER_POSITION_STARTING_DATE_CONSTRAINT                               = "messages.error.bad-request.career-position.starting-date-constraint";
    public static final String ERROR_BAD_REQUEST_CAREER_POSITION_CAREER_POSITIONS_CHAINING_CONSTRAINT                   = "messages.error.bad-request.career-position.career-positions-chaining-constraint";
    public static final String ERROR_BAD_REQUEST_ONBOARDING_PROCESS_INITIALIZED_TEAM_MEMBER_CONSTRAINT                  = "messages.error.bad-request.onboarding-process.initialized-team-member-constraint";
    public static final String ERROR_BAD_REQUEST_ONBOARDING_PROCESS_FRESH_RECRUIT_CONSTRAINT                            = "messages.error.bad-request.onboarding-process.fresh-recruit-constraint";
    public static final String ERROR_BAD_REQUEST_ONBOARDING_PROCESS_UNIQUE_INITIATION_CONSTRAINT                        = "messages.error.bad-request.onboarding-process.unique-initiation-constraint";
    public static final String ERROR_BAD_REQUEST_ONBOARDING_PROCESS_SECRET_WORD_VALIDATION_CONSTRAINT                   = "messages.error.bad-request.onboarding-process.secret-word-validation-constraint";
    public static final String ERROR_BAD_REQUEST_ONBOARDING_PROCESS_SECRET_WORD_NOT_MATCHED                             = "messages.error.bad-request.onboarding-process.secret-word-not-matched";
    public static final String ERROR_BAD_REQUEST_ONBOARDING_PROCESS_RATING_CONSTRAINT                                   = "messages.error.bad-request.onboarding-process.rating-constraint";
    public static final String ERROR_BAD_REQUEST_MENTORSHIP_TERMINATED_MENTORSHIP_UPDATE_CONSTRAINT                     = "messages.error.bad-request.mentorship.terminated-mentorship-update-constraint";
    public static final String ERROR_BAD_REQUEST_MENTORSHIP_RATING_CONSTRAINT                                           = "messages.error.bad-request.mentorship.rating-constraint";
    public static final String ERROR_BAD_REQUEST_MENTORSHIP_FEEDBACK_LENGTH_CONSTRAINT                                  = "messages.error.bad-request.mentorship.feedback-length-constraint";
    public static final String ERROR_BAD_REQUEST_MENTORSHIP_TERMINATING_MENTORSHIP_CONSTRAINT                           = "messages.error.bad-request.mentorship.terminating-mentorship-constraint";
    public static final String ERROR_BAD_REQUEST_RESPONSE_SUBMISSION_ENABLED_QUESTION_CONSTRAINT                        = "messages.error.bad-request.response-submission.enabled-question-constraint";
    public static final String ERROR_BAD_REQUEST_RESPONSE_SUBMISSION_UNIQUE_RESPONSE_CONSTRAINT                         = "messages.error.bad-request.response-submission.unique-response-constraint";
    public static final String ERROR_BAD_REQUEST_RESPONSE_SUBMISSION_CONTENT_CONSTRAINT                                 = "messages.error.bad-request.response-submission.content-constraint";
    public static final String ERROR_BAD_REQUEST_RESPONSE_SUBMISSION_RATING_CONSTRAINT                                  = "messages.error.bad-request.response-submission.rating-constraint";
    public static final String ERROR_BAD_REQUEST_RESPONSE_SUBMISSION_CHOICES_CONSTRAINT                                 = "messages.error.bad-request.response-submission.choices-constraint";
    public static final String ERROR_BAD_REQUEST_RESPONSE_SUBMISSION_SINGLE_CHOICE_CONSTRAINT                           = "messages.error.bad-request.response-submission.single-choice-constraint";
    public static final String ERROR_BAD_REQUEST_EVALUATION_EVALUATION_CURRENT_CAREER_POSITION_CONSTRAINT               = "messages.error.bad-request.evaluation.evaluation-current-career-position-constraint";
    public static final String ERROR_BAD_REQUEST_EVALUATION_UNIQUE_OPEN_EVALUATION_CONSTRAINT                           = "messages.error.bad-request.evaluation.unique-open-evaluation-constraint";
    public static final String ERROR_BAD_REQUEST_EVALUATION_CLOSED_EVALUATION_UPDATE_CONSTRAINT                         = "messages.error.bad-request.evaluation.closed-evaluation-update-constraint";
    public static final String ERROR_BAD_REQUEST_EVALUATION_EVALUATION_DATE_CONSTRAINT                                  = "messages.error.bad-request.evaluation.evaluation-date-constraint";
    public static final String ERROR_BAD_REQUEST_EVALUATION_RATING_CONSTRAINT                                           = "messages.error.bad-request.evaluation.rating-constraint";
    public static final String ERROR_BAD_REQUEST_EVALUATION_FEEDBACK_LENGTH_CONSTRAINT                                  = "messages.error.bad-request.evaluation.feedback-length-constraint";
    public static final String ERROR_BAD_REQUEST_EVALUATION_CLOSING_EVALUATION_CONSTRAINT                               = "messages.error.bad-request.evaluation.closing-evaluation-constraint";
    public static final String ERROR_BAD_REQUEST_TEAM_TEAM_EVALUATION_DATE_VALIDITY_CONSTRAINT                          = "messages.error.bad-request.team.team-evaluation-date-validity-constraint";

    public static final String ERROR_UNAUTHORIZED                                                                       = "messages.error.unauthorized";
    public static final String ERROR_UNAUTHORIZED_WRONG_PASSWORD                                                        = "messages.error.unauthorized.wrong-password";

    public static final String ERROR_FORBIDDEN_ACCOUNT_NOT_ACTIVE                                                       = "messages.error.forbidden.account-not-active";
    public static final String ERROR_FORBIDDEN_ACCOUNT_ACTIVATION                                                       = "messages.error.forbidden.account-activation";
    public static final String ERROR_FORBIDDEN_ACCOUNT_ACTIVATION_MAIL_SENDING                                          = "messages.error.forbidden.account-activation-mail-sending";
    public static final String ERROR_FORBIDDEN_ACCOUNT_STATUS_UPDATE                                                    = "messages.error.forbidden.account-status-update";
    public static final String ERROR_FORBIDDEN_PASSWORD_RESET                                                           = "messages.error.forbidden.password-reset";

    public static final String ERROR_NOT_FOUND_RESOURCE_NOT_FOUND_FOR_ID                                                = "messages.error.not-found.resource-not-found-for-id";
    public static final String ERROR_NOT_FOUND_RESOURCE_NOT_FOUND                                                       = "messages.error.not-found.resource-not-found";
    public static final String ERROR_NOT_FOUND_ACCOUNT_NOT_FOUND                                                        = "messages.error.not-found.account-not-found";

    public static final String ERROR_SERVICE_UNAVAILABLE_MAIL_SENDING                                                   = "messages.error.service-unavailable.mail-sending";

    public static final String MAIL_GREETING                                                                            = "messages.mail.greeting";
    public static final String MAIL_SIGNATURE                                                                           = "messages.mail.signature";
    public static final String MAIL_QUIZ_URL                                                                            = "messages.mail.quiz-url";
    public static final String MAIL_QUIZ_URL_SUBJECT                                                                    = "messages.mail.quiz-url.subject";
    public static final String MAIL_QUIZ_URL_CONTENT                                                                    = "messages.mail.quiz-url.content";

    public static final String MAIL_ACCOUNT_ACTIVATION_SUBJECT                                                          = "messages.mail.account-activation.subject";
    public static final String MAIL_ACCOUNT_ACTIVATION_CONTENT                                                          = "messages.mail.account-activation.content";

    public static final String MAIL_PASSWORD_RESET_SUBJECT                                                              = "messages.mail.password-reset.subject";
    public static final String MAIL_PASSWORD_RESET_CONTENT                                                              = "messages.mail.password-reset.content";

    public static final String MAIL_ONBOARDING_PROCESS_SUBJECT                                                          = "messages.mail.onboarding-process.subject";
    public static final String MAIL_ONBOARDING_PROCESS_CONTENT                                                          = "messages.mail.onboarding-process.content";

    public static final String MAIL_SIGNUP_ACTIVATION_SUBJECT                                                           = "messages.mail.signUp-account-activation.subject";
    public static final String MAIL_SIGNUP_ACTIVATION_CONTENT                                                           = "messages.mail.signUp-account-activation.content";

    public static final String MAIL_DOCUMENT_REQUEST_SUBJECT                                                            = "messages.mail.document-request.subject";
    public static final String MAIL_DOCUMENT_REQUEST_CONTENT                                                            = "messages.mail.document-request.content";

    public static final String MAIL_DOCUMENT_INVALIDATED_RESPONSE_SUBJECT                                               = "messages.mail.document-invalidateResponse.subject";
    public static final String MAIL_DOCUMENT_INVALIDATED_RESPONSE_CONTENT                                               = "messages.mail.document-invalidateResponse.content";

    public static final String MAIL_DOCUMENT_VALIDATED_RESPONSE_SUBJECT                                               = "messages.mail.document-validateResponse.subject";
    public static final String MAIL_DOCUMENT_VALIDATED_RESPONSE_CONTENT                                               = "messages.mail.document-validateResponse.content";

    public static final String MAIL_DOCUMENT_VALIDATED_SYSTEM_SUBJECT                                                   = "messages.mail.document-validated-system.subject";
    public static final String MAIL_DOCUMENT_VALIDATED_SYSTEM_CONTENT                                                   = "messages.mail.document-validated-system.content";

    public static final String MAIL_CANCELED_EVENT_SUBJECT                                                              = "messages.mail.canceled-event.subject";
    public static final String MAIL_CANCELED_EVENT_CONTENT                                                              = "messages.mail.canceled-even.content";

    public static final String MAIL_UPDATE_EVENT_SUBJECT                                                              = "messages.mail.updated-event.subject";
    public static final String MAIL_UPDATE_EVENT_CONTENT                                                              = "messages.mail.updated-even.content";

    public static final String MAIL_QUIZ_URLSTRING                                                                            = "messages.mail.quiz-url";
    public static final String MAIL_QUIZ_URL_SUBJECTSTRING                                                                    = "messages.mail.quiz-url.subject";
    public static final String MAIL_QUIZ_URL_CONTENTSTRING                                                                    = "messages.mail.quiz-url.content";
    private MessagesConstants() {

    }

}
