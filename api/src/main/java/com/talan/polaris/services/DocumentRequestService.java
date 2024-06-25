package com.talan.polaris.services;

import com.talan.polaris.dto.DocumentResuestsStaticsDTO;
import com.talan.polaris.entities.DocumentRequestEntity;
import com.talan.polaris.enumerations.RequestStatusEnum;
import com.talan.polaris.enumerations.RequestTypeEnum;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Collection;


public interface DocumentRequestService{
    public DocumentRequestEntity sendDocumentRequest(Long createdById, Long collaboratorId, String requestMotif, RequestTypeEnum type,Boolean createdByRh) throws Exception;
    public Collection<DocumentRequestEntity> findDocumentRequests();
    public Collection<DocumentRequestEntity> findDocumentsByCollaboratorIdAndType(Long collaboratorId , Long type );
    public Collection<DocumentRequestEntity> getInWaitingDocumentsRequests();
    public Collection<DocumentRequestEntity> getProcessedDocumentsRequests();
    public DocumentRequestEntity cancelDocumentRequest(DocumentRequestEntity documentRequestEntity);
    public DocumentRequestEntity  documentRequestRejection(Long hrResponibleId, DocumentRequestEntity documentRequest);
    public DocumentRequestEntity create(DocumentRequestEntity documentRequestEntity);
    public DocumentRequestEntity update(DocumentRequestEntity documentRequestEntity);
    public DocumentRequestEntity findById(Long id);
    public DocumentRequestEntity validateDocumentRequest(Long hrResponibleId ,DocumentRequestEntity documentRequestEntity) throws Exception ;
    public Collection<DocumentRequestEntity> validateAllDocumentRequest(Long hrResponibleId ,Collection<DocumentRequestEntity> documentRequestEntity) throws Exception ;
    public Collection<DocumentRequestEntity> getRequestsWithoutTemplate(RequestStatusEnum status,RequestTypeEnum type,Boolean template);
    public int generateDocumentsWithoutTemplate(RequestTypeEnum type);
    public void validateDocumentRequestBySystem() throws IOException;
    public String generateDocumentReferences(DocumentRequestEntity documentRequestEntity) ;
    public Collection<DocumentRequestEntity> getInWaitingRequestsWithTemplate( );
    public Resource download(Collection<DocumentRequestEntity> documentRequests,String filename);
    public int getCollabsRequestsByStatus(Long collabId, RequestStatusEnum status);
    public Collection<DocumentRequestEntity> getCollabRequests(Long collabId);
    public Collection<DocumentResuestsStaticsDTO> getCollabRequestsStstics(Long collabId);
    public DocumentRequestEntity receivedDocumentRequest(DocumentRequestEntity documentRequestEntity);
    public Long countDocumentBySatus(RequestStatusEnum status, Long collabId);





}
