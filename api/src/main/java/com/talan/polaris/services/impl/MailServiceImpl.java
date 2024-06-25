package com.talan.polaris.services.impl;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.talan.polaris.dto.CalendarRequest;
import com.talan.polaris.entities.CollaboratorEntity;
import com.talan.polaris.entities.EventEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.talan.polaris.exceptions.MailSendingException;
import com.talan.polaris.services.MailService;

import java.io.File;
import java.io.IOException;
import java.time.*;
import java.util.Date;
import java.util.List;

import static com.talan.polaris.constants.CommonConstants.*;
import static com.talan.polaris.constants.ConfigurationConstants.*;
import static com.talan.polaris.constants.MailConstants.*;
import static com.talan.polaris.constants.MessagesConstants.*;
import static com.talan.polaris.enumerations.SessionTypeEnum.SessionTypeConstants.ACCOUNT_ACTIVATION_SESSION_TYPE;
import static com.talan.polaris.enumerations.SessionTypeEnum.SessionTypeConstants.PASSWORD_RESET_SESSION_TYPE;
/**
 * MailServiceImpl.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Service
public class MailServiceImpl implements MailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final MessageSource messageSource;
    private final String mailTemplatePath;
    private final String mailQuizLinkTemplatePath;
    private final String passwordInitializationLink;
    private final String userManagementLink;
    private final String documentRequestLink;
    private final String documentResponseLink;
    private  final String eventLink;
    @Value("${" + MAIL_FROM + "}")
    private String mailFrom;

    @Autowired
    public MailServiceImpl(
            JavaMailSender mailSender,
            TemplateEngine templateEngine,
            MessageSource messageSource,
            @Value("${" + MAIL_TEMPLATE_PATH + "}")
                    String mailTemplatePath,
            @Value("${" + MAIL_QUIZ_LINK_TEMPLATE_PATH + "}")
                    String mailQuizLinkTemplatePath,
            @Value("${" + SECURITY_PASSWORD_INITIALIZATION_LINK + "}")
                    String passwordInitializationLink,
            @Value("${" + USER_MANAGEMENT_LINK + "}")
                    String userManagementLink ,
            @Value("${" + DOCUMENT_REQUEST_LINK + "}")
                    String documentRequestLink,
            @Value("${" + DOCUMENT_RESPONSE_LINK + "}")
                    String documentResponseLink,
            @Value("${" + EVENT_LINK + "}")
                    String eventLink)



    {


        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.messageSource = messageSource;
        this.mailTemplatePath = mailTemplatePath;
        this.mailQuizLinkTemplatePath = mailQuizLinkTemplatePath;
        this.passwordInitializationLink = passwordInitializationLink;
        this.userManagementLink=userManagementLink;
        this.documentRequestLink = documentRequestLink;
        this.documentResponseLink = documentResponseLink;
        this.eventLink = eventLink;

    }

    @Override
    public void sendAccountActivationMail(
            String receiverFirstName,
            String receiverEmail,
            String sessionId){

        this.sendMail(
                receiverEmail,
                MAIL_ACCOUNT_ACTIVATION_SUBJECT,
                receiverFirstName,
                String.format(
                        this.messageSource.getMessage(
                                MAIL_ACCOUNT_ACTIVATION_CONTENT,
                                null,
                                LOCALE_DEFAULT),
                        String.format(
                                this.passwordInitializationLink,
                                ACCOUNT_ACTIVATION_SESSION_TYPE,
                                sessionId)));

    }

    @Override
    public void sendValidateResponseMail(String collaboratorName, String reference, String senderFirstName, String senderLastName,
                                         String receiverEmail,  String requestType, String requestDate,
                                         String validatedByFirstName, String validatedByLastName ) {
        this.sendMail(
                receiverEmail,
                MAIL_DOCUMENT_VALIDATED_RESPONSE_SUBJECT,
                collaboratorName,
                String.format(
                        this.messageSource.getMessage(
                                MAIL_DOCUMENT_VALIDATED_RESPONSE_CONTENT,
                                null,
                                LOCALE_DEFAULT),reference, senderFirstName,senderLastName, requestDate, validatedByFirstName,
                        validatedByLastName, requestType,
                        String.format(
                                this.documentResponseLink
                        )));
    }

    @Override
    public void sendRequestAccountActivationMail(
            String receiverFirstName,
            String receiverEmail,
            String collaboratorInformation
    ) {
        this.sendMail(
                receiverEmail,
                MAIL_SIGNUP_ACTIVATION_SUBJECT,
                receiverFirstName,
                String.format(
                        this.messageSource.getMessage(
                                MAIL_SIGNUP_ACTIVATION_CONTENT,
                                null,
                                LOCALE_DEFAULT),
                        collaboratorInformation,
                        this.userManagementLink));

    }

    @Override
    public void sendDocumentRequestMail(String receiverFirstName, String receiverEmail, String collabFirstName,
                                        String collabLastName, String typeLabel, String requestMotif) {

        this.sendRequestMail(
                receiverEmail,
                MAIL_DOCUMENT_REQUEST_SUBJECT,
                receiverFirstName,
                String.format(
                        this.messageSource.getMessage(
                                MAIL_DOCUMENT_REQUEST_CONTENT,
                                null,
                                LOCALE_DEFAULT),collabFirstName, collabLastName, typeLabel, requestMotif,
                        String.format(
                                this.documentRequestLink
                        )),

                collabFirstName,
                collabLastName
        );
    }

    @Override
    public void sendInvalidateResponseMail(String receiverFirstName, String receiverEmail, String senderFirstName, String senderLastName,
                                           String typeRequest, String requestMotif) {
        this.sendMail(
                receiverEmail,
                MAIL_DOCUMENT_INVALIDATED_RESPONSE_SUBJECT,
                receiverFirstName,
                String.format(
                        this.messageSource.getMessage(
                                MAIL_DOCUMENT_INVALIDATED_RESPONSE_CONTENT,
                                null,
                                LOCALE_DEFAULT), senderFirstName,senderLastName,typeRequest, requestMotif));


    }

    @Override
    public void sendCanceledEventMailToParticipate(String receiverFirstName, String receiverEmail, String eventTitle,
                                                   String eventCreator, String deleteMotif) {
        this.sendMail(
                receiverEmail,
                MAIL_CANCELED_EVENT_SUBJECT,
                receiverFirstName,
                String.format(
                        this.messageSource.getMessage(
                                MAIL_CANCELED_EVENT_CONTENT ,
                                null,
                                LOCALE_DEFAULT), eventTitle, eventCreator, deleteMotif));


    }

    @Override
    public void sendUpdateEventMailToParticipate(String receiverFirstName, String receiverEmail, String eventTitle, String eventCreator) {
        this.sendMail(
                receiverEmail,
                MAIL_UPDATE_EVENT_SUBJECT,
                receiverFirstName,
                String.format(
                        this.messageSource.getMessage(
                                MAIL_UPDATE_EVENT_CONTENT ,
                                null,
                                LOCALE_DEFAULT), eventTitle, eventCreator,  String.format(
                                this.eventLink)
                ));
    }

    @Override
    public void sendPasswordResetMail(
            String receiverFirstName,
            String receiverEmail,
            String sessionId) {

        this.sendMail(
                receiverEmail,
                MAIL_PASSWORD_RESET_SUBJECT,
                receiverFirstName,
                String.format(
                        this.messageSource.getMessage(
                                MAIL_PASSWORD_RESET_CONTENT,
                                null,
                                LOCALE_DEFAULT),
                        String.format(
                                this.passwordInitializationLink,
                                PASSWORD_RESET_SESSION_TYPE,
                                sessionId)));

    }

    @Override
    public void sendQuizLinkMail(
            String receiverFirstName,
            String receiverEmail,
            String quizUrl) {

        this.sendQuizLinkMail(
                receiverEmail,
                MAIL_QUIZ_URL_SUBJECT,
                receiverFirstName,
                String.format(
                        this.messageSource.getMessage(
                                MAIL_QUIZ_URL_CONTENT,
                                null,
                                LOCALE_DEFAULT)),
                quizUrl
        ) ;

    }

    @Override
    @Async
    public void sendOnboardingProcessMail(
            String receiverFirstName,
            String receiverEmail,
            String freshRecruitName,
            String secretWordPart) {

        this.sendMail(
                receiverEmail,
                MAIL_ONBOARDING_PROCESS_SUBJECT,
                receiverFirstName,
                String.format(
                        this.messageSource.getMessage(
                                MAIL_ONBOARDING_PROCESS_CONTENT,
                                null,
                                LOCALE_DEFAULT),
                        freshRecruitName,
                        secretWordPart));

    }

    private void sendMail(
            final String receiverEmail,
            final String mailSubjectCode,
            final String receiverFirstName,
            final String localizedMailContent){

        final String mailTemplate = this.buildMailTemplate(
                String.format(this.messageSource.getMessage(
                        MAIL_GREETING,
                        null,
                        LOCALE_DEFAULT), receiverFirstName),
                localizedMailContent,
                this.messageSource.getMessage(
                        MAIL_SIGNATURE,
                        null,
                        LOCALE_DEFAULT));

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();

        try {

            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                    mimeMessage,
                    true,
                    ENCODING_UTF8);

            mimeMessageHelper.setTo(receiverEmail);

            mimeMessageHelper.setSubject(this.messageSource.getMessage(
                    mailSubjectCode,
                    null,
                    LOCALE_DEFAULT));

            mimeMessageHelper.setText(mailTemplate, true);
            mimeMessageHelper.setFrom(this.mailFrom);
            this.mailSender.send(mimeMessage);

        } catch (Exception exception) {
            LOGGER.debug("{Mail Sending}",exception);
            throw new MailSendingException(exception);
        }

    }

    private void sendQuizLinkMail(
            final String receiverEmail,
            final String mailSubjectCode,
            final String receiverFirstName,
            final String localizedMailContent,
            final String quizUrl ){

        final String mailTemplate = this.buildMailQuizLinkTemplate(
                String.format(this.messageSource.getMessage(
                        MAIL_GREETING,
                        null,
                        LOCALE_DEFAULT), receiverFirstName),
                localizedMailContent,
                String.format(this.messageSource.getMessage(
                        MAIL_QUIZ_URL,
                        null,
                        LOCALE_DEFAULT),quizUrl));

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();

        try {

            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                    mimeMessage,
                    true,
                    ENCODING_UTF8);

            mimeMessageHelper.setTo(receiverEmail);

            mimeMessageHelper.setSubject(this.messageSource.getMessage(
                    mailSubjectCode,
                    null,
                    LOCALE_DEFAULT));

            mimeMessageHelper.setText(mailTemplate, true);
            mimeMessageHelper.setFrom(this.mailFrom);
            this.mailSender.send(mimeMessage);

        } catch (Exception exception) {
            LOGGER.debug("{Mail Sending}",exception);
            throw new MailSendingException(exception);
        }

    }


    private String buildMailTemplate(
            final String localizedMailGreeting,
            final String localizedMailContent,
            final String localizedMailSignature) {

        final Context context = new Context();

        context.setVariable(MAIL_GREETING_TEMPLATE_TAG, localizedMailGreeting);
        context.setVariable(MAIL_CONTENT_TEMPLATE_TAG, localizedMailContent);
        context.setVariable(MAIL_SIGNATURE_TEMPLATE_TAG, localizedMailSignature);

        return templateEngine.process(this.mailTemplatePath, context);

    }
    private String buildMailQuizLinkTemplate(
            final String localizedMailGreeting,
            final String localizedMailContent,
            final String quizUrl) {

        final Context context = new Context();

        context.setVariable(MAIL_GREETING_TEMPLATE_TAG, localizedMailGreeting);
        context.setVariable(MAIL_CONTENT_TEMPLATE_TAG, localizedMailContent);
        context.setVariable(MAIL_QUIZ_URL_TEMPLATE_TAG, quizUrl);

        return templateEngine.process(this.mailQuizLinkTemplatePath, context);

    }

    private void sendRequestMail(
            final String receiverEmail,
            final String mailSubjectCode,
            final String receiverFirstName,
            final String localizedMailContent,
            final String collabFirstName,
            final String collabLastName){

        final String mailTemplate = this.buildMailTemplate(
                String.format(this.messageSource.getMessage(
                        MAIL_GREETING,
                        null,
                        LOCALE_DEFAULT), receiverFirstName),
                localizedMailContent,
                this.messageSource.getMessage(
                        MAIL_SIGNATURE,
                        null,
                        LOCALE_DEFAULT));

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        try {
            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                    mimeMessage,
                    true,
                    ENCODING_UTF8);
            mimeMessageHelper.setTo(receiverEmail);
            mimeMessageHelper.setSubject(String.format(this.messageSource.getMessage(
                    mailSubjectCode,
                    null,
                    LOCALE_DEFAULT), collabFirstName, collabLastName));
            mimeMessageHelper.setText(mailTemplate, true);
            mimeMessageHelper.setFrom(this.mailFrom);
            this.mailSender.send(mimeMessage);
        } catch (Exception exception) {
            LOGGER.debug("{Mail Sending}",exception);
            throw new MailSendingException(exception);
        }

    }

    //mail validate all
    @Override
    public void sendValidatedRequestBySystemToBOMail(String receiverFirstName, String receiverEmail,
                                                     List<String> attachmentsPath, String validateDtate) throws IOException {

        this.sendMailAttachment(
                receiverEmail,
                MAIL_DOCUMENT_VALIDATED_SYSTEM_SUBJECT,
                receiverFirstName,
                String.format(
                        this.messageSource.getMessage(
                                MAIL_DOCUMENT_VALIDATED_SYSTEM_CONTENT,
                                null,
                                LOCALE_DEFAULT),validateDtate
                ),
                attachmentsPath
        );
    }

    private void sendMailAttachment(
            final String receiverEmail,
            final String mailSubjectCode,
            final String receiverFirstName,
            final String localizedMailContent,
            final List<String> attachmentsPath) {
        final String mailTemplate = this.buildMailTemplate(
                String.format(this.messageSource.getMessage(
                        MAIL_GREETING,
                        null,
                        LOCALE_DEFAULT), receiverFirstName),
                localizedMailContent,
                this.messageSource.getMessage(
                        MAIL_SIGNATURE,
                        null,
                        LOCALE_DEFAULT));

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        try {

            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                    mimeMessage,
                    true,
                    ENCODING_UTF8);

            mimeMessageHelper.setTo(receiverEmail);

            mimeMessageHelper.setSubject(this.messageSource.getMessage(
                    mailSubjectCode,
                    null,
                    LOCALE_DEFAULT));

            mimeMessageHelper.setText(mailTemplate, true);

            mimeMessageHelper.setFrom(this.mailFrom);
            for (String f : attachmentsPath) {
                FileSystemResource file = new FileSystemResource(new File(f));
                mimeMessageHelper.addAttachment(file.getFilename(), file);
            }
            this.mailSender.send(mimeMessage);

        } catch (Exception exception) {
            LOGGER.debug("{Mail Sending}",exception);
            throw new MailSendingException(exception);
        }

    }

    /**
     * spam mail
     *
     * @param event
     * @param participant
     * @throws Exception
     */
    @Override
    public void sendEventMailOrganizer(EventEntity event, CollaboratorEntity participant) throws Exception {
        OutlookEventMail calendarService = new OutlookEventMail(mailSender);
        LocalDateTime meetingStart = convertToLocalDateTime(event.getDate());
        calendarService.sendCalendarInvite(
                mailFrom,
                new CalendarRequest.Builder()
                        .withSubject(EVENT_SIRIUS_MAIL + event.getTitle())
                        .withBody(getEventInformation(event))
                        .withToEmail(participant.getEmail())
                        .withMeetingStartTime(convertToLocalDateTime(event.getDate()))
                        .withMeetingEndTime(meetingStart.plusHours(24))
                        .build(), EVENT_MAIL_SUPPORT, event.getTitle());
    }

    /**
     * @param event
     * @param participant
     * @throws Exception
     */
    @Override
    public void sendEventMailParticipant(EventEntity event, CollaboratorEntity participant) throws Exception {
        OutlookEventMail calendarService = new OutlookEventMail(mailSender);
        LocalDateTime meetingStart = convertToLocalDateTime(event.getDate());
        calendarService.sendCalendarInvite(
                event.getCreator().getEmail(),
                new CalendarRequest.Builder()
                        .withSubject(EVENT_SIRIUS_MAIL + event.getTitle())
                        .withBody(getEventInformation(event))
                        .withToEmail(participant.getEmail())
                        .withMeetingStartTime(convertToLocalDateTime(event.getDate()))
                        .withMeetingEndTime(meetingStart.plusHours(24))
                        .build(), event.getCreator().getFirstName() + " " + event.getCreator().getLastName(), event.getTitle());
    }


    /**
     * event Mail body
     * @param event
     * @return
     */

    public String getEventInformation(EventEntity event) {

        String information = null;
        if (event != null) {
            information = EVENT_MAIL_BODY + event.getTitle() + " added by " + event.getCreator().getFirstName() + "  " + event.getCreator().getLastName() + ", which will take place in " + event.getLocation() + " on " + event.getDate() + ". \n Have a good day!";
        }
        return information;
    }

    /**
     * convert date to localdate
     * @param date
     * @return
     */
    public LocalDateTime convertToLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }


}
