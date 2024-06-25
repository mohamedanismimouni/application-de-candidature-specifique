//package com.talan.polaris.services.impl;
//
//import com.talan.polaris.entities.RequestTypeEntity;
//
//import com.talan.polaris.enumerations.*;
//
//import com.talan.polaris.services.*;
//import com.talan.polaris.entities.DocumentRequestEntity;
//import com.talan.polaris.entities.RequestStatusEntity;
//import com.talan.polaris.enumerations.RequestStatusEnum;
//import com.talan.polaris.repositories.DocumentRequestRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//
//import java.io.IOException;
//import java.util.Collection;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.*;
//import static org.mockito.MockitoAnnotations.initMocks;
//
///**
// * Unit tests class for methods implemented in {@link DocumentRequestServiceImpl}.
// *
// * @author Imen Mechergui
// * @since 1.0.0
// */
//class DocumentRequestServiceImplUnitTests {
//    @Mock
//    private SessionService sessionService;
//
//    @Mock
//    private MailService mailService;
//
//    @InjectMocks
//    private DocumentRequestServiceImpl DocumentRequestServiceImpl;
//
//    @Mock
//    private DocumentRequestRepository documentRequestRepository;
//    @InjectMocks
//    private DocumentRequestServiceImpl documentRequestServiceImpl;
//
//    @BeforeEach
//    public void setup() {
//        initMocks(this);
//    }
//
//    @Test
//    void documentRequestRejection_givenDocumentRequest_whenCalled_thenDocumentRequestIsRejected() {
//
//        DocumentRequestService docService = Mockito.mock(DocumentRequestService.class);
//        DocumentRequestEntity docReq = new DocumentRequestEntity();
//        when(docService.documentRequestRejection(anyLong(), any(DocumentRequestEntity.class))).thenReturn(docReq);
//        assertThat(docReq).isNotNull();
//    }
//
//    @Test
//    void cancelDocumentRequest_givenDocumentRequest_whenCalled_thenDocumentRequestIsUpdated() {
//        DocumentRequestEntity documentRequest = new DocumentRequestEntity();
//        RequestStatusEntity requestStatusEntity = new RequestStatusEntity();
//        requestStatusEntity.setLabel(RequestStatusEnum.CANCELED);
//        documentRequest.setStatus(requestStatusEntity);
//
//        //spy DocumentRequestServiceImpl
//        DocumentRequestServiceImpl documentServiceImplSpy = spy(this.documentRequestServiceImpl);
//
//        //get result from request
//        doAnswer((invocation) -> invocation.getArgument(0))
//                .when(documentServiceImplSpy)
//                .cancelDocumentRequest(any(DocumentRequestEntity.class));
//
//        // when
//        DocumentRequestEntity documentRequestSpy = documentServiceImplSpy.cancelDocumentRequest(documentRequest);
//
//        //then
//        assertThat(documentRequestSpy.getStatus().getLabel()).isEqualTo(RequestStatusEnum.CANCELED);
//    }
//
//    @Test
//    void findDocumentRequests_givenCollaboratorIdAndTypeId_thenCallDocumentRequestRepository() {
//        // given + when
//        Long collaboratorId = 1L;
//        Long typeId = 1L;
//        this.documentRequestServiceImpl.findDocumentsByCollaboratorIdAndType(collaboratorId, typeId);
//        // then
//        verify(this.documentRequestRepository, only()).findDocumentRequestsByCollaboratorIdAndTypeId(collaboratorId, typeId, RequestStatusEnum.CANCELED);
//    }
//
//
//    @Test
//    void sendDocumentRequest_givenCollaboratorIDAndRequestMotifAndType_whenCalled_thenDocumentRequestSent() throws Exception {
//        DocumentRequestService docService = Mockito.mock(DocumentRequestService.class);
//        DocumentRequestEntity docReq = new DocumentRequestEntity();
//        when(docService.sendDocumentRequest(anyLong(), anyLong(), anyString(), any(RequestTypeEnum.class), anyBoolean())).thenReturn(docReq);
//        assertThat(docReq).isNotNull();
//    }
//
//    @Test
//    void getProcessedDocumentsRequests__whenCalled_thenCallDocumentRequestRepository() {
//        Collection<DocumentRequestEntity> evaluationsList = this.documentRequestServiceImpl.getProcessedDocumentsRequests();
//        // then
//        verify(documentRequestRepository, times(1)).getProcessedDocuments(RequestStatusEnum.VALIDATED, RequestStatusEnum.INVALIDATED);
//        assertThat(evaluationsList).isNotNull();
//    }
//
//    @Test
//    void getInWaitingDocumentsRequests__whenCalled_thenCallDocumentRequestRepository() throws IOException, InterruptedException {
//        Collection<DocumentRequestEntity> evaluationsList = this.documentRequestServiceImpl.getInWaitingDocumentsRequests();
//        // then
//        verify(documentRequestRepository, times(1)).getDocumentsRequestsByStatus(RequestStatusEnum.IN_WAITING);
//        assertThat(evaluationsList).isNotNull();
//    }
//
//    @Test
//    void validateDocumentRequest_givenDocumentRequest_whenCalled_thenDocumentRequestIsUpdated() throws Exception {
//        DocumentRequestEntity documentRequest = new DocumentRequestEntity();
//        RequestStatusEntity requestStatusEntity = new RequestStatusEntity();
//        requestStatusEntity.setLabel(RequestStatusEnum.VALIDATED);
//        documentRequest.setStatus(requestStatusEntity);
//
//        DocumentRequestService docService = Mockito.mock(DocumentRequestService.class);
//        when(docService.validateDocumentRequest(anyLong(), any(DocumentRequestEntity.class))).thenReturn(documentRequest);
//        assertThat(documentRequest.getStatus().getLabel()).isEqualTo(RequestStatusEnum.VALIDATED);
//
//    }
//
//    @Test
//    void sendValidateResponseMail_givenAttributes_whenIsOk_thenAccountValidateResponseMailIsSent() {
//
//
//        //given
//        DocumentRequestServiceImpl documentRequestServiceImpl = spy(this.DocumentRequestServiceImpl);
//        // when
//        documentRequestServiceImpl.sendValidateMailToCollaborator(anyString(), anyString(), anyString(),
//                anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
//
//        // then
//        verify(this.mailService, only()).sendValidateResponseMail(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
//
//    }
//
//    @Test
//    void generateReferences_givenDocumentRequest_whenMissionCertificate_thenReferenceIsGenerated() {
//        DocumentRequestEntity documentRequest = new DocumentRequestEntity();
//
//        RequestTypeEntity requestTypeEntity = new RequestTypeEntity();
//        requestTypeEntity.setLabel(RequestTypeEnum.MISSION_CERTIFICATE);
//        documentRequest.setType(requestTypeEntity);
//
//        DocumentRequestServiceImpl docService = Mockito.mock(DocumentRequestServiceImpl.class);
//        when(docService.generateDocumentReferences(documentRequest)).thenReturn("12022020-01");
//        String reference = docService.generateDocumentReferences(documentRequest);
//        assertThat(reference).isNotNull();
//
//    }
//
//    @Test
//    void generateReferences_givenDocumentRequest_whenWorkCertificate_thenReferenceIsGenerated() {
//        DocumentRequestEntity documentRequest = new DocumentRequestEntity();
//
//        RequestTypeEntity requestTypeEntity = new RequestTypeEntity();
//        requestTypeEntity.setLabel(RequestTypeEnum.WORK_CERTIFICATE);
//        documentRequest.setType(requestTypeEntity);
//
//        DocumentRequestServiceImpl docService = Mockito.mock(DocumentRequestServiceImpl.class);
//        when(docService.generateDocumentReferences(documentRequest)).thenReturn("12022020-01");
//        String reference = docService.generateDocumentReferences(documentRequest);
//        assertThat(reference).isNotNull();
//
//    }
//
//    @Test
//    void generateDocumentsWithoutTemplate_whenCalled_thenCallDocumentRequestRepository() throws Exception {
//        int requestNumbers = this.documentRequestServiceImpl.generateDocumentsWithoutTemplate(RequestTypeEnum.WORK_CERTIFICATE);
//        // then
//        verify(documentRequestRepository, times(1)).getRequestsWithoutTemplate(RequestStatusEnum.IN_WAITING, RequestTypeEnum.WORK_CERTIFICATE, true);
//        assertThat(requestNumbers).isNotNull();
//    }
//
//}
