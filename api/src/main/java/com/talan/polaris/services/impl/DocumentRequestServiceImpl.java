package com.talan.polaris.services.impl;

import com.talan.polaris.constants.CommonConstants;
import com.talan.polaris.dto.DocumentResuestsStaticsDTO;
import com.talan.polaris.dto.keycloakmodelsdto.KeycloakUserDTO;
import com.talan.polaris.entities.*;
import com.talan.polaris.enumerations.*;
import com.talan.polaris.exceptions.MailSendingException;
import com.talan.polaris.repositories.DocumentRequestRepository;
import com.talan.polaris.services.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.talan.polaris.services.DocumentRequestService;
import com.talan.polaris.services.RequestStatusService;
import com.talan.polaris.services.RequestTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

@Service
public class DocumentRequestServiceImpl implements DocumentRequestService {

    private final DocumentRequestRepository documentRequestRepository;
    private final RequestStatusService requestStatusService;
//    private final SessionService sessionService;
//    private final MailService mailService;
    private final RequestTypeService requestTypeService;
    private final CollaboratorService userService;
    private final GenerateDocumentService generateDocumentService;
    private final ParametrageAppliService parametrageAppliService;
    private final CollaboratorAPIByblosService collabservice;
    private final CollaboratorService collaboratorService;
    private final KeycloakService keycloakservice;


    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentRequestServiceImpl.class);
    private static final String MAIL_PROBLEM = "problem of sending validated request mail!";

    @Autowired
    public DocumentRequestServiceImpl(
            DocumentRequestRepository repository,
            RequestStatusService requestStatusService,
//            SessionService sessionService,
//            MailService mailService,
            RequestTypeService requestTypeService,
            CollaboratorService userService,
            GenerateDocumentService generateDocumentService,
            ParametrageAppliService parametrageAppliService,
            CollaboratorAPIByblosService collabservice,
            CollaboratorService collaboratorService,
            KeycloakService keycloakService

    ) {
        this.documentRequestRepository = repository;
        this.requestStatusService = requestStatusService;
//        this.sessionService = sessionService;
//        this.mailService = mailService;
        this.requestTypeService = requestTypeService;
        this.userService = userService;
        this.generateDocumentService = generateDocumentService;
        this.parametrageAppliService = parametrageAppliService;
        this.collabservice=collabservice;
        this.collaboratorService = collaboratorService;
        this.keycloakservice = keycloakService;
    }

    @Override
    public DocumentRequestEntity create(DocumentRequestEntity documentRequestEntity) {
        return this.documentRequestRepository.saveAndFlush(documentRequestEntity);
    }

    @Override
    @Transactional
    public DocumentRequestEntity sendDocumentRequest(Long createdById, Long collaboratorId, String requestMotif, RequestTypeEnum label, Boolean createdByRh) throws Exception {
        LOGGER.info("Send Document Request");
        DocumentRequestEntity request = new DocumentRequestEntity();
        CollaboratorEntity user = this.userService.findById(collaboratorId);
        CollaboratorEntity collaboratorEntity = user;
        RequestStatusEntity status = this.requestStatusService.getStatusByLabel(RequestStatusEnum.IN_WAITING);
        request.setStatus(status);
        request.setCollaborator(collaboratorEntity);
        RequestTypeEntity type = this.requestTypeService.getTypeByLabel(label);
        request.setType(type);
        request.setRequestMotif(requestMotif);
        request.setCreatedByRH(createdByRh);
        request.setCreatedBy(this.userService.findById(createdById));
        DocumentRequestEntity documentRequest = create(request);
        String documentType = CommonConstants.SALARY_CERTIFICATE;
        if (type.getLabel() != RequestTypeEnum.SALARY_CERTIFICATE) {
            documentType = CommonConstants.WORK_CERTIFICATE;
        }
        //generate document
        documentRequest.setIdEDM(generateDocumentService.generateDocument(documentRequest));
        if (documentRequest.getIdEDM() != null && !documentRequest.getCreatedByRH()) {
            try {
                sendMailToRH(collaboratorEntity.getFirstName(), collaboratorEntity.getLastName(), documentType, requestMotif);
            } catch (MailSendingException e) {
                LOGGER.error("problem of sending registration mail!");
            }
        }
        return update(documentRequest);
    }

    @Override
    public Collection<DocumentRequestEntity> findDocumentRequests() {
        return this.documentRequestRepository.getDocumentsRequestsByStatus(RequestStatusEnum.IN_WAITING);
    }


    @Override
    public DocumentRequestEntity receivedDocumentRequest(DocumentRequestEntity documentRequestEntity) {
        RequestStatusEntity status = this.requestStatusService.getStatusByLabel(RequestStatusEnum.RECEIVED);
        documentRequestEntity.setStatus(status);
        return documentRequestRepository.save(documentRequestEntity);
    }

    public void sendMailToRH(String collabFirstName, String collabLastName, String typeLabel, String requestMotif) {

        Collection<KeycloakUserDTO> hrResponsibles = this.keycloakservice.findUsersByClientRole(ProfileTypeEnum.HR_RESPONSIBLE);
        for (KeycloakUserDTO user : hrResponsibles) {
        	CollaboratorEntity rh = this.collaboratorService.findUserByEmail(user.getEmail());
            if (rh.getAccountStatus() == AccountStatusEnum.ACTIVE) {
                try {
                    /*this.mailService.sendDocumentRequestMail(rh.getFirstName(), rh.getEmail(),
                            collabFirstName, collabLastName, typeLabel, requestMotif);*/
                } catch (MailSendingException e) {
                    throw e;
                }
            }

        }
    }

    @Override
    public Collection<DocumentRequestEntity> getInWaitingDocumentsRequests() {
        LOGGER.info("In waiting Document Request");
        return documentRequestRepository.getDocumentsRequestsByStatus(RequestStatusEnum.IN_WAITING);
    }

    @Override
    public Collection<DocumentRequestEntity> getProcessedDocumentsRequests() {
        LOGGER.info("Processed Document Request");
        return documentRequestRepository.getProcessedDocuments(RequestStatusEnum.VALIDATED, RequestStatusEnum.INVALIDATED ,RequestStatusEnum.RECEIVED);
    }

    @Override
    public DocumentRequestEntity update(DocumentRequestEntity documentRequestEntity) {
        return this.documentRequestRepository.saveAndFlush(documentRequestEntity);
    }

    @Override
    public DocumentRequestEntity findById(Long id) {
        Optional<DocumentRequestEntity> documentRequestEntity = this.documentRequestRepository.findById(id);
        if (documentRequestEntity.isPresent()) {
            return documentRequestEntity.get();
        }
        return null;
    }

    @Override
    public DocumentRequestEntity documentRequestRejection(Long hrResponibleId, DocumentRequestEntity documentRequest) {
        RequestStatusEntity status = this.requestStatusService.getStatusByLabel(RequestStatusEnum.INVALIDATED);
        documentRequest.setStatus(status);
        //send notification to collab
//        String sessionID = this.sessionService.createSession(documentRequest.getCollaborator().getEmail(),
//                SessionTypeEnum.ACCOUNT_ACTIVATION);
        CollaboratorEntity hrResponsible = userService.findById(hrResponibleId);
        String documentType = CommonConstants.SALARY_CERTIFICATE;
        if (documentRequest.getType().getLabel() != RequestTypeEnum.SALARY_CERTIFICATE) {
            documentType = CommonConstants.WORK_CERTIFICATE;
        }
        try {
            sendInvalidateMailToCollaborator(documentRequest.getCollaborator().getFirstName(), documentRequest.getCollaborator().getEmail(),
                    hrResponsible.getFirstName(), hrResponsible.getLastName(), documentType, documentRequest.getRejectionMotif()
            );
        } catch (MailSendingException e) {
            LOGGER.error("problem of sending canceled request mail!");
        }
        return update(documentRequest);
    }

    @Override
    public Collection<DocumentRequestEntity> findDocumentsByCollaboratorIdAndType(Long collaboratorId, Long type) {
        return this.documentRequestRepository.findDocumentRequestsByCollaboratorIdAndTypeId(collaboratorId, type, RequestStatusEnum.CANCELED);
    }

    @Override
    public DocumentRequestEntity cancelDocumentRequest(DocumentRequestEntity documentRequestEntity) {
        RequestStatusEntity status = this.requestStatusService.getStatusByLabel(RequestStatusEnum.CANCELED);
        documentRequestEntity.setStatus(status);
        return documentRequestRepository.save(documentRequestEntity);
    }


    public void sendInvalidateMailToCollaborator(String collabFirstName, String email, String senderFirstName, String senderLastName,
                                                 String typeRequest, String requestMotif) {
        try {
            /*this.mailService.sendInvalidateResponseMail(collabFirstName, email,
                     senderFirstName, senderLastName,
                    typeRequest, requestMotif);*/

        } catch (MailSendingException e) {
//            this.sessionService.deleteSessionById(sessionID);
            throw e;
        }
    }

    /**
     * @param collabName
     * @param reference
     * @param rhFirstName
     * @param rhLastName
     * @param email
     * @param typeRequest
     * @param requestDate
     * @param validatedByFirstName
     * @param validatedByLastName
     */
    public void sendValidateMailToCollaborator(String collabName, String reference, String rhFirstName, String rhLastName, String email,
                                               String typeRequest, String requestDate, String validatedByFirstName, String validatedByLastName) {

        try {
           /* this.mailService.sendValidateResponseMail(collabName, reference, rhFirstName, rhLastName, email,
                     typeRequest, requestDate, validatedByFirstName, validatedByLastName);*/

        } catch (MailSendingException e) {
            throw e;
        }
    }

    @Override
    public String generateDocumentReferences(DocumentRequestEntity documentRequestEntity) {

        // Gets the current date
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        //Get increment
        Instant instant = Instant.now();
        Instant result = instant.atOffset(ZoneOffset.UTC)
                .with(LocalTime.of(0, 0, 0, instant.getNano()))
                .toInstant();

        int increment = this.documentRequestRepository.countDocumentsByType(documentRequestEntity.getType().getLabel(), result, RequestStatusEnum.VALIDATED) + 1;
        String reference = "";
        if (increment < 10) {
            reference = date.format(formatter) + "-0" + increment;

        } else {
            reference = date.format(formatter) + "-" + increment;

        }
        return reference;
    }

    @Override
    public Collection<DocumentRequestEntity> getInWaitingRequestsWithTemplate() {
        return documentRequestRepository.getInWaitingRequestsWithTemplate(RequestStatusEnum.IN_WAITING);
    }

    @Transactional
    @Override
    public DocumentRequestEntity validateDocumentRequest(Long hrResponibleId, DocumentRequestEntity documentRequestEntity) throws Exception {
        try {
            RequestStatusEntity status = this.requestStatusService.getStatusByLabel(RequestStatusEnum.VALIDATED);
            CollaboratorEntity user = this.userService.findById(documentRequestEntity.getCollaborator().getId());
            CollaboratorEntity collaboratorEntity = user;
            documentRequestEntity.setCollaborator(collaboratorEntity);

            documentRequestEntity.setStatus(status);
            documentRequestEntity.setValidateddBySystem(false);
            //generate request reference

            documentRequestEntity.setValidatedAt(Instant.now());
            documentRequestEntity.setReference(this.generateDocumentReferences(documentRequestEntity));
            documentRequestEntity.setIdEDM(this.generateDocumentService.addReferenceToDocument(documentRequestEntity));
            Long idEdmEtiquette = generateDocumentService.EtiquetteGenerate(documentRequestEntity);
            if (idEdmEtiquette != null && idEdmEtiquette != -1L) {
                documentRequestEntity.setIdEtiquetteEDM(generateDocumentService.EtiquetteGenerate(documentRequestEntity));
            }
            //send notification to collaborator
            CollaboratorEntity hrResponsible = userService.findById(hrResponibleId);
            this.sendValidateMail(documentRequestEntity, hrResponsible.getFirstName(), hrResponsible.getLastName());
            //generate and save etiquette


            return documentRequestRepository.save(documentRequestEntity);

        } catch (Exception e) {
            return documentRequestRepository.save(documentRequestEntity);

        }

    }

    @Override
    public Collection<DocumentRequestEntity>  validateAllDocumentRequest(Long hrResponsibleId, Collection<DocumentRequestEntity> documentRequestEntity) throws Exception {
        Collection<DocumentRequestEntity> requests = new ArrayList<>();
        for (DocumentRequestEntity request : documentRequestEntity) {
            DocumentRequestEntity requestValidated = validateDocumentRequest(hrResponsibleId, request);
            requests.add(requestValidated);
        }

        return requests;
    }

    @Override
    public Collection<DocumentRequestEntity> getRequestsWithoutTemplate(RequestStatusEnum status, RequestTypeEnum type, Boolean template) {
        LOGGER.info("Get all requests without template");
        return documentRequestRepository.getRequestsWithoutTemplate(status, type, template);
    }

    @Override
    public int generateDocumentsWithoutTemplate(RequestTypeEnum type) {
        LOGGER.info("Generate document for request without template");
        Collection<DocumentRequestEntity> requestsWithoutTemplate = null;
        //het requests without template
        requestsWithoutTemplate = documentRequestRepository.getRequestsWithoutTemplate(RequestStatusEnum.IN_WAITING, type, true);
        if (!requestsWithoutTemplate.isEmpty()) {
            for (DocumentRequestEntity request : requestsWithoutTemplate) {
                //generate document by request
                try {
                    request.setIdEDM(generateDocumentService.generateDocument(request));
                } catch (Exception e) {
                    LOGGER.error("generate Documents Without Template method=>Problem to generate document request !");
                }
                request.setWithoutTemplate(false);
                //save EDM information
                this.update(request);
            }
            return requestsWithoutTemplate.size();
        } else
            return 0;

    }

    /**
     * validate in waiting requests by SYSTEM
     */
    @Override
    @Transactional
    public void validateDocumentRequestBySystem() {
        Collection<DocumentRequestEntity> validateRequests = new ArrayList<>();
        try {
            LOGGER.info("validate Document Request By System Method");
            Collection<DocumentRequestEntity> inWaitingRequests = this.getInWaitingRequestsWithTemplate();
            if (!inWaitingRequests.isEmpty()) {
                for (DocumentRequestEntity documentRequestEntity : inWaitingRequests) {
                    if (documentRequestEntity.getCollaborator()!=null /*&& documentRequestEntity.getCollaborator().getCollab() != null*/ /*&& collabservice.findCollabById(documentRequestEntity.getCollaborator().getCollab().getId())!=null*/&& collaboratorService.findById(documentRequestEntity.getCollaborator().getId())!=null) {
                        ParametrageAppliEntity parametrageAppliDureeEntity = parametrageAppliService.findByParametre(ParametrageAppliEnum.DUREE_AUTO_VALIDATION_DEMANDE_ADMIN);
                        if (parametrageAppliDureeEntity != null) {
                            int days = Integer.parseInt(parametrageAppliDureeEntity.getValeurParam());
                            if (this.differenceDaysBetweenDates(documentRequestEntity.getCreatedAt()).getDays() >= days || this.differenceDaysBetweenDates(documentRequestEntity.getCreatedAt()).getMonths() >= 1 || this.differenceDaysBetweenDates(documentRequestEntity.getCreatedAt()).getYears() >= 1) {
                                //set validated status
                                RequestStatusEntity status = this.requestStatusService.getStatusByLabel(RequestStatusEnum.VALIDATED);
                                documentRequestEntity.setStatus(status);
                                //validated by system
                                documentRequestEntity.setValidateddBySystem(true);
                                //generate request reference
                                String reference = this.generateDocumentReferences(documentRequestEntity);
                                documentRequestEntity.setReference(reference);
                                //save new file in EDM
                                documentRequestEntity.setIdEDM(this.generateDocumentService.addReferenceToDocument(documentRequestEntity));
                                LOGGER.info("Generate File Reference");
                                // etiquette
                                Long idEdmEtiquette = generateDocumentService.EtiquetteGenerate(documentRequestEntity);
                                if (idEdmEtiquette != null && idEdmEtiquette != -1L) {
                                    documentRequestEntity.setIdEtiquetteEDM(generateDocumentService.EtiquetteGenerate(documentRequestEntity));
                                    LOGGER.info("Generate File Etiquette");
                                }
                                //save entity
                                documentRequestEntity.setValidatedAt(Instant.now());
                                validateRequests.add(this.update(documentRequestEntity));
                                LOGGER.info("save document Request Entity");
                                //send mail to collaborator
                                this.sendValidateMail(documentRequestEntity, "System", "");
                                LOGGER.info("Send Collaborators Mail");

                            }
                        } else {
                            LOGGER.error("the validation duration parameter does not exist in the database");
                        }
                    }
                    else
                    {
                        LOGGER.error("Document Request with NULL/NOT exists Collaborator");
                    }
                }
            } else {
                LOGGER.error("Empty In waiting List !");

            }
            //send mail to BO
            if (!validateRequests.isEmpty()) {
                this.sendMailWithAttachement(validateRequests);
                LOGGER.info("Send BO Mail with Attachment");
            }
        } catch (Exception e) {
            LOGGER.error("Auto-validation catch : {} " ,e.getMessage());
        }
    }

    public void sendValidateMail(DocumentRequestEntity documentRequestEntity, String rhFirstName, String rhLastName) {
//        String sessionID = this.sessionService.createSession(documentRequestEntity.getCollaborator().getEmail(), SessionTypeEnum.ACCOUNT_ACTIVATION);
        //format request Date
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.FRANCE).withZone(ZoneId.systemDefault());
        String createdAt = formatter.format(documentRequestEntity.getCreatedAt());
        //documentType
        String documentType = CommonConstants.SALARY_CERTIFICATE;
        if (documentRequestEntity.getType().getLabel() != RequestTypeEnum.SALARY_CERTIFICATE) {
            documentType = CommonConstants.WORK_CERTIFICATE;
        }

        try {
            if (documentRequestEntity.getCreatedBy() != null) {
                sendValidateMailToCollaborator(documentRequestEntity.getCollaborator().getFirstName(),
                        documentRequestEntity.getReference(),
                        documentRequestEntity.getCreatedBy().getFirstName(),
                        documentRequestEntity.getCreatedBy().getLastName(),
                        documentRequestEntity.getCollaborator().getEmail(),
                        documentType,
                        createdAt,
                        rhFirstName,
                        rhLastName

                );
            } else {
                if (documentRequestEntity.getCreatedByRH()) {
                    sendValidateMailToCollaborator(documentRequestEntity.getCollaborator().getFirstName(),
                            documentRequestEntity.getReference(),
                            "Back office",
                           "",
                            documentRequestEntity.getCollaborator().getEmail(),
                            documentType,
                            createdAt,
                            rhFirstName,
                            rhLastName
                    );
                } else {
                    sendValidateMailToCollaborator(documentRequestEntity.getCollaborator().getFirstName(),
                            documentRequestEntity.getReference(),
                            documentRequestEntity.getCollaborator().getFirstName(),
                            documentRequestEntity.getCollaborator().getLastName(),
                            documentRequestEntity.getCollaborator().getEmail(),
                            documentType,
                           createdAt,
                            rhFirstName,
                            rhLastName
                    );
                }
            }
        } catch (MailSendingException e) {
            LOGGER.error(MAIL_PROBLEM);
        }

    }
    public Period differenceDaysBetweenDates(Instant instantDate) {
        LocalDate localDte = instantDate
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate now = LocalDate.now();
        return Period.between(localDte, now);
    }

    /**
     * @param requestEntities
     * @throws IOException
     */
    public void sendMailWithAttachement(Collection<DocumentRequestEntity> requestEntities) throws Exception {
        ArrayList<String> attachmentsPath = this.generateDocumentService.convertByteToFile(requestEntities);
        Instant instant = Instant.now();
        Date myDate = Date.from(instant);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String formattedValidateDate = formatter.format(myDate);
        sendMailToAllBO(attachmentsPath, formattedValidateDate);
        this.generateDocumentService.deleteLocalFile(attachmentsPath);
    }

    public void sendMailToAllBO(List<String> attachmentsPath, String validateDtate) {

        Collection<KeycloakUserDTO> hrResponsibles = this.keycloakservice.findUsersByClientRole(ProfileTypeEnum.HR_RESPONSIBLE);
        //send mail to BO list
        for (KeycloakUserDTO user : hrResponsibles) {
        	CollaboratorEntity rh = this.collaboratorService.findUserByEmail(user.getEmail());
            if (rh.getAccountStatus() == AccountStatusEnum.ACTIVE) {
                /*try {
                    this.mailService.sendValidatedRequestBySystemToBOMail(rh.getFirstName(), rh.getEmail(), attachmentsPath, validateDtate);
                } catch (MailSendingException | IOException e) {
                    LOGGER.error((e.getMessage()));
                }*/
            }

        }
    }

    @Override
    public Resource download(Collection<DocumentRequestEntity> documentRequests ,String filename) {
        try {


            File etiquette = generateDocumentService.createEtiquetteMergedFile();
            FileOutputStream etiquetteFos  = new FileOutputStream(etiquette);
            generateDocumentService.mergeEtiquette(documentRequests,etiquette,etiquetteFos);


            LOGGER.info("Start download etiquette file");


            Resource resource = new UrlResource(etiquette.toURI().toURL());

            LOGGER.info("File path: {}",etiquette.toURI().toURL());
            if (resource.exists() || resource.isReadable()) {
                LOGGER.info("get the resource file");
                LOGGER.info("End download etiquette file");

                return resource;
            } else {
                LOGGER.info("Could not read the file!");
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public int getCollabsRequestsByStatus(Long collabId, RequestStatusEnum status) {
        return this.documentRequestRepository.getCollabsRequestsByStatus(collabId,status).size();
    }

    @Override
    public Collection<DocumentRequestEntity> getCollabRequests(Long collabId) {
        return this.documentRequestRepository.getCollabRequests(collabId);
    }

    @Override
    public Collection<DocumentResuestsStaticsDTO> getCollabRequestsStstics(Long collabId) {
        Collection<DocumentResuestsStaticsDTO> staticsList = new ArrayList<>();
        DocumentResuestsStaticsDTO inwaitingStatics = new DocumentResuestsStaticsDTO();
        DocumentResuestsStaticsDTO validatedStatics = new DocumentResuestsStaticsDTO();
        DocumentResuestsStaticsDTO receivedStatics = new DocumentResuestsStaticsDTO();
        Long inwaitingSum =0L;
        Long validatedSum =0L;
        Long receivedSum = 0L;
        for(DocumentRequestEntity request: this.documentRequestRepository.getCollabRequests(collabId) ){
            if(request.getStatus().getLabel().equals(RequestStatusEnum.IN_WAITING)){
                inwaitingSum += 1;
            }
            else if(request.getStatus().getLabel().equals(RequestStatusEnum.VALIDATED)){
                validatedSum += 1;
            }
            else if(request.getStatus().getLabel().equals(RequestStatusEnum.RECEIVED)){
                receivedSum += 1;
            }
        }
        //inwaiting
        inwaitingStatics.setStatus(RequestStatusEnum.IN_WAITING);
        inwaitingStatics.setSum(inwaitingSum);
        staticsList.add(inwaitingStatics);
        //valideted
        validatedStatics.setStatus(RequestStatusEnum.VALIDATED);
        validatedStatics.setSum(validatedSum);
        staticsList.add(validatedStatics);
        //received
        receivedStatics.setStatus(RequestStatusEnum.RECEIVED);
        receivedStatics.setSum(receivedSum);
        staticsList.add(receivedStatics);
        return staticsList;
    }
    @Override
    public Long countDocumentBySatus(RequestStatusEnum status, Long collabId) {
        return documentRequestRepository.countDocumentBySatus(status, collabId);
    }

}