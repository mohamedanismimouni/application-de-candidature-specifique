package com.talan.polaris.controllers;
import com.talan.polaris.dto.DocumentRequestDTO;
import com.talan.polaris.dto.DocumentResuestsStaticsDTO;
import com.talan.polaris.dto.RequestTypeDTO;
import com.talan.polaris.enumerations.RequestStatusEnum;
import com.talan.polaris.enumerations.RequestTypeEnum;
import com.talan.polaris.mapper.DocumentRequestMapper;
import com.talan.polaris.services.DocumentRequestService;
import com.talan.polaris.utils.validation.groups.CreateValidationGroup;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.talan.polaris.constants.CommonConstants.ETIQUETTE_FILE_NAME;
import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;
import static com.talan.polaris.enumerations.ProfileTypeEnum.ProfileTypeConstants.COLLABORATOR_PROFILE_TYPE;
import static com.talan.polaris.enumerations.ProfileTypeEnum.ProfileTypeConstants.HR_RESPONSIBLE_PROFILE_TYPE;

@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/requests")
public class DocumentRequestController {

    private final DocumentRequestService documentRequestService;
    private final ModelMapper modelMapper;

    @Autowired
    public DocumentRequestController(DocumentRequestService documentRequestService, ModelMapper modelMapper) {
        this.documentRequestService = documentRequestService;
        this.modelMapper=modelMapper;
    }

    @PostMapping(path = "/{createdById}/{collaboratorId}/{requestType}/{createdByRh}")
    public DocumentRequestDTO sendDocumentRequest(@PathVariable(value = "createdById", required = true) Long createdById,
                                                  @PathVariable(value = "collaboratorId", required = true) Long collaboratorId,
                                                  @PathVariable(value = "requestType", required = true) RequestTypeEnum requestType,
                                                  @PathVariable(value = "createdByRh", required = true) Boolean createdByRh,
                                                  @RequestBody(required = false) String requestMotif) throws Exception {
        return DocumentRequestMapper.convertRequestEntityToDTO(this.documentRequestService.sendDocumentRequest(createdById,collaboratorId,requestMotif,requestType,createdByRh),modelMapper);
    }


    @GetMapping
    public Collection<DocumentRequestDTO> findDocumentsByCollaboratorIdAndDocumentType(
            @RequestParam(value = "collaboratorId", required = true) Long collaboratorId,
            @RequestParam(value = "type", required = true) Long type ) {

        return this.documentRequestService.findDocumentsByCollaboratorIdAndType(collaboratorId,type)
                .stream()
                .map(documentRequest-> DocumentRequestMapper.convertRequestEntityToDTO(documentRequest,modelMapper))
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/inWaiting")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "')")
    public Collection<DocumentRequestDTO> getInWaitingDocumentsRequests() {
        return DocumentRequestMapper.convertDocumentsRequestListToDTO(this.documentRequestService.getInWaitingDocumentsRequests(),modelMapper);

    }

    @GetMapping(path = "/processed")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "')")
    public Collection<DocumentRequestDTO> getProcessedDocumentsRequests() {
        return DocumentRequestMapper.convertDocumentsRequestListToDTO(this.documentRequestService.getProcessedDocumentsRequests(),modelMapper);

    }


    @PostMapping(path = "/rejection/{hrResponsibleId}")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "')")
    public DocumentRequestDTO documentsRequestRejection(@PathVariable(value = "hrResponsibleId", required = true) Long hrResponsibleId,@RequestBody @Validated(CreateValidationGroup.class) DocumentRequestDTO documentRequestDTO) {
        return  DocumentRequestMapper.convertRequestEntityToDTO(this.documentRequestService.documentRequestRejection(hrResponsibleId,DocumentRequestMapper.convertRequestDTOToEntity(documentRequestDTO,modelMapper)),modelMapper);


    }


    @PostMapping(path="/cancel")
    public DocumentRequestDTO cancelDocumentRequest(@RequestBody @Validated(CreateValidationGroup.class) DocumentRequestDTO documentRequestDTO) {
        return DocumentRequestMapper.convertRequestEntityToDTO(this.documentRequestService.cancelDocumentRequest(DocumentRequestMapper.convertRequestDTOToEntity(documentRequestDTO,modelMapper)),modelMapper);
    }


    @PostMapping(path="/validate/{hrResponsibleId}")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "')")
    public DocumentRequestDTO validateDocumentRequest(@PathVariable(value = "hrResponsibleId", required = true) Long hrResponsibleId,@RequestBody @Validated(CreateValidationGroup.class) DocumentRequestDTO documentRequestDTO) throws Exception {
        return DocumentRequestMapper.convertRequestEntityToDTO(this.documentRequestService.validateDocumentRequest(hrResponsibleId,DocumentRequestMapper.convertRequestDTOToEntity(documentRequestDTO,modelMapper)),modelMapper);
    }

    @PostMapping(path="/validateAll/{hrResponsibleId}")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "')")
    public Collection<DocumentRequestDTO> validateAllDocumentRequest(@PathVariable(value = "hrResponsibleId", required = true) Long hrResponsibleId,@RequestBody @Validated(CreateValidationGroup.class) Collection<DocumentRequestDTO> documentRequestDTO) throws Exception {
        return DocumentRequestMapper.convertDocumentsRequestListToDTO(this.documentRequestService.validateAllDocumentRequest(hrResponsibleId,DocumentRequestMapper.convertDocumentsRequestListToEntity(documentRequestDTO,modelMapper)),modelMapper);
    }

    @PostMapping(path="/validateAll/download")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "')")
    public ResponseEntity<Resource> downloadEtiquetteMerged(@RequestBody @Validated(CreateValidationGroup.class) Collection<DocumentRequestDTO> documentRequestDTO) throws IOException {
        System.out.println(documentRequestDTO);
        Resource file = documentRequestService.download(DocumentRequestMapper.convertDocumentsRequestListToEntity(documentRequestDTO,modelMapper), ETIQUETTE_FILE_NAME);
        Path path = file.getFile()
                .toPath();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }



    @PostMapping(path="/withoutTemplate")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "')")
    public int requestsWithoutTemplates(@RequestBody @Validated RequestTypeDTO requestTypeDTO) {
        return this.documentRequestService.generateDocumentsWithoutTemplate(requestTypeDTO.getLabel());
    }

    @PostMapping(path="/{collabId}/{status}")
    public int getCollabsRequestsByStatus(@PathVariable(value = "collabId", required = true) Long collabId,
                                                                        @PathVariable(value = "status", required = true) RequestStatusEnum status) {
        return this.documentRequestService.getCollabsRequestsByStatus(collabId,status);
    }

    @PostMapping(path="/{collabId}")
    public Collection<DocumentRequestDTO> getCollabsRequests(@PathVariable(value = "collabId", required = true) Long collabId) {
        return DocumentRequestMapper.convertDocumentsRequestListToDTO(this.documentRequestService.getCollabRequests(collabId), modelMapper);
    }

    @PostMapping(path="/statics/{collabId}")
    public Collection<DocumentResuestsStaticsDTO> getCollabsRequestsStatics(@PathVariable(value = "collabId", required = true) Long collabId) {
        return this.documentRequestService.getCollabRequestsStstics(collabId);
    }

    @PostMapping(path="/received")
    public DocumentRequestDTO receivedDocumentRequest(@RequestBody @Validated(CreateValidationGroup.class) DocumentRequestDTO documentRequestDTO) {
        return DocumentRequestMapper.convertRequestEntityToDTO(this.documentRequestService.receivedDocumentRequest(DocumentRequestMapper.convertRequestDTOToEntity(documentRequestDTO,modelMapper)),modelMapper);
    }

    @GetMapping(path = "/KPIDocument/{status}/{collabId}")
    @PreAuthorize("hasRole('" + COLLABORATOR_PROFILE_TYPE + "')")
    public Long countDocumentBySatus(@PathVariable(value = "status", required = true) RequestStatusEnum status, @PathVariable(value = "collabId", required = true) Long collabId) {
        return this.documentRequestService.countDocumentBySatus(status, collabId);

    }
}
