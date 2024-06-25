package com.talan.polaris.controllers;

import com.talan.polaris.dto.RequestTypeDTO;
import com.talan.polaris.enumerations.RequestTypeEnum;
import com.talan.polaris.mapper.RequestTypeMapper;
import com.talan.polaris.services.RequestTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;
import static com.talan.polaris.enumerations.ProfileTypeEnum.ProfileTypeConstants.*;

/**
 * A controller defining team resource endpoints.
 *
 * @author Imen Mechergui
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/requestsTypes")
public class RequestTypeController {

    private final RequestTypeService requestTypeService;
    private final ModelMapper modelMapper;

    @Autowired
    public RequestTypeController(RequestTypeService requestTypeService, ModelMapper modelMapper) {
        this.requestTypeService = requestTypeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public Collection<RequestTypeDTO> getRequestsType() {
        return RequestTypeMapper.convertRequestTypeEntityListToDTO(this.requestTypeService.getVisibleRequestType(), modelMapper);

    }

    @GetMapping(path = "/getTemplates")
    public Collection<RequestTypeDTO> getTemplateRequestsType() {
        return RequestTypeMapper.convertRequestTypeEntityListToDTO(this.requestTypeService.getTemplateRequestType(), modelMapper);

    }


    @PostMapping(path = "/updateRequestType")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "')")
    public RequestTypeDTO saveEDMid(@RequestBody(required = false) RequestTypeDTO requestTypeDTO) {
        return RequestTypeMapper.convertRequestTypeEntityToDTO(this.requestTypeService.update(RequestTypeMapper.convertRequestTypeDTOToEntity(requestTypeDTO, modelMapper)), modelMapper);

    }

    @GetMapping(path = "/lastUploadPayRoll")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "')")
    public Date getLastDateUploadPayRoll() {
        return this.requestTypeService.payRollLastUpload(RequestTypeEnum.PAYROLL);

    }

    @GetMapping(path = "/lastUploadReminder")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "')")
    public Date getLastDateUploadReminder() {
        return this.requestTypeService.payRollLastUpload(RequestTypeEnum.REMINDER);

    }
    @GetMapping(path = "/getTypeByLabel/{requestType}")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "') OR hasRole('" + SUPPORT_PROFILE_TYPE + "')"+ " OR hasRole('" +COLLABORATOR_PROFILE_TYPE + "')")
    public RequestTypeDTO getTypeByLabel(@PathVariable(value = "requestType", required = true) RequestTypeEnum requestType) {
        return RequestTypeMapper.convertRequestTypeEntityToDTO(this.requestTypeService.getTypeByLabel(requestType), modelMapper);

    }
}
