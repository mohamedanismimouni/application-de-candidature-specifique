//package com.talan.polaris.services;
//
//
//import com.talan.polaris.entities.CollaboratorEntity;
//import com.talan.polaris.entities.EventEntity;
//
//import java.io.IOException;
//import java.util.List;
//
//
///**
// * MailService.
// *
// * @author Nader Debbabi
// * @since 1.0.0
// */
//public interface MailService {
//
//    public void sendAccountActivationMail(
//            String receiverFirstName,
//            String receiverEmail,
//            String sessionId);
//
//    public void sendPasswordResetMail(
//            String receiverFirstName,
//            String receiverEmail,
//            String sessionId);
//
//    void sendQuizLinkMail(
//            String receiverFirstName,
//            String receiverEmail,
//            String quizUrl);
//
//    public void sendOnboardingProcessMail(
//            String receiverFirstName,
//            String receiverEmail,
//            String freshRecruitName,
//            String secretWordPart);
//
//    public void sendRequestAccountActivationMail(
//            String receiverFirstName,
//            String receiverEmail,
//            String collaboratorInformation);
//
//    public void sendDocumentRequestMail(
//            String receiverFirstName,
//            String receiverEmail,
//            String collabFirstName,
//            String collabLastName,
//            String typeLabel,
//            String requestMotif);
//
//    public void sendInvalidateResponseMail(
//            String receiverFirstName,
//            String receiverEmail,
//            String senderFirstName,
//            String senderLastName,
//            String requestType,
//            String requestMotif);
//
//    public void sendCanceledEventMailToParticipate(
//            String receiverFirstName,
//            String receiverEmail,
//            String eventTitle,
//            String eventCreator,
//            String deleteMotif);
//
//    public void sendUpdateEventMailToParticipate(
//            String receiverFirstName,
//            String receiverEmail,
//            String eventTitle,
//            String eventCreator);
//
//
//    public void sendValidateResponseMail(
//            String collaboratorName,
//            String reference,
//            String receiverFirstName,
//            String receiverLastName,
//            String receiverEmail,
//            String requestType,
//            String requestDate,
//            String validatedByFirstName,
//            String validatedByLastName);
//
//    public void sendValidatedRequestBySystemToBOMail(
//            String receiverFirstName,
//            String receiverEmail,
//            List<String> attachmentsPath,
//            String validateDtate) throws IOException;
//    public void sendEventMailOrganizer(EventEntity event, CollaboratorEntity participant) throws Exception;
//    public void sendEventMailParticipant(EventEntity event, CollaboratorEntity participant) throws Exception ;
//
//}
