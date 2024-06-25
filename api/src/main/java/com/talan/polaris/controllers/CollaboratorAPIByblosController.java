
package com.talan.polaris.controllers;

import com.talan.polaris.dto.CollaboratorUserDTO;
import com.talan.polaris.mapper.CollaboratorMapper;
import com.talan.polaris.services.CollaboratorAPIByblosService;
import com.talan.polaris.services.CollaboratorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;
import static com.talan.polaris.enumerations.ProfileTypeEnum.ProfileTypeConstants.HR_RESPONSIBLE_PROFILE_TYPE;
import static com.talan.polaris.enumerations.ProfileTypeEnum.ProfileTypeConstants.SUPPORT_PROFILE_TYPE;

@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/collab")
public class CollaboratorAPIByblosController {


    private final CollaboratorAPIByblosService collaboratorAPIByblosService;
    private final CollaboratorService collaboratorService;
    private final ModelMapper modelMapper;

    @Autowired
    public CollaboratorAPIByblosController(CollaboratorAPIByblosService collaboratorAPIByblosService, ModelMapper modelMapper,
                                           CollaboratorService collaboratorService) {
        this.collaboratorAPIByblosService = collaboratorAPIByblosService;
        this.modelMapper = modelMapper;
        this.collaboratorService = collaboratorService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "') OR hasRole('" + SUPPORT_PROFILE_TYPE + "')")
    public Collection<CollaboratorUserDTO> getCollaborators() {
        return this.collaboratorService.findAll()
                .stream()
                .map(collab -> CollaboratorMapper.convertCollabEntityToCollabUserDTO(collab, modelMapper))
                .collect(Collectors.toList());

    }

    @GetMapping(path = "/synchronizeCollab")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "') OR hasRole('" + SUPPORT_PROFILE_TYPE + "')")
    public Collection<CollaboratorUserDTO> synchronizeCollaborators() {
        return this.collaboratorAPIByblosService.synchronizeCollaborators()
                .stream()
                .map(collab -> CollaboratorMapper.convertCollabEntityToCollabUserDTO(collab, modelMapper))
                .collect(Collectors.toList());

    }

    @GetMapping(path = "/collaboratorsUser")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "') OR hasRole('" + SUPPORT_PROFILE_TYPE + "')")
    public Collection<CollaboratorUserDTO> getCollaboratorsWithUser() {
        return this.collaboratorAPIByblosService.findAllCollabWithUser()
                .stream()
                .map(collab -> CollaboratorMapper.convertCollabEntityToCollabUserDTO(collab, modelMapper))
                .collect(Collectors.toList());
    }
}
