package com.talan.polaris.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.talan.polaris.dto.AcquiredSkillDTO;
import com.talan.polaris.dto.AcquiredSkillLevelDTO;
import com.talan.polaris.mapper.AcquiredSkillLevelMapper;
import com.talan.polaris.mapper.AcquiredSkillMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talan.polaris.services.AcquiredSkillService;

import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;

/**
 * A controller defining acquired skill resource endpoints.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/acquired-skills")
@Validated
public class AcquiredSkillController {
    private final ModelMapper modelMapper;

    private final AcquiredSkillService acquiredSkillService;

    @Autowired
    public AcquiredSkillController(ModelMapper modelMapper, AcquiredSkillService acquiredSkillService) {
        this.modelMapper = modelMapper;
        this.acquiredSkillService = acquiredSkillService;
    }

    @GetMapping(params = { "collaboratorId" })
    public Collection<AcquiredSkillDTO> getAcquiredSkills(
            @RequestParam(value = "collaboratorId", required = true) Long collaboratorId) {

        return this.acquiredSkillService.findAcquiredSkillsByCollaboratorId(collaboratorId)
                .stream()
                .map(acquiredSkill ->  AcquiredSkillMapper.convertRequiredSkillToDTO(acquiredSkill,modelMapper))
                .collect(Collectors.toList());

    }

    @PostMapping()
    public Collection<AcquiredSkillDTO> createAcquiredSkills(
            @RequestBody @NotEmpty @Valid Collection<AcquiredSkillDTO> acquiredSkillsDTO) {

        return this.acquiredSkillService.createAcquiredSkills(acquiredSkillsDTO.stream()
                        .map(acquiredSkill ->  AcquiredSkillMapper.convertRequiredSkillToEntity(acquiredSkill,modelMapper))
                        .collect(Collectors.toList()))
                .stream()
                .map(acquiredSkillDTO ->  AcquiredSkillMapper.convertRequiredSkillToDTO(acquiredSkillDTO,modelMapper))
                .collect(Collectors.toList());

    }

    @PostMapping(path = "/{acquiredSkillId}/progress")
    public AcquiredSkillLevelDTO createAcquiredSkillLevel(
            @PathVariable(value = "acquiredSkillId", required = true) String acquiredSkillId,
            @RequestBody @Valid AcquiredSkillLevelDTO acquiredSkillLevel) {

        return AcquiredSkillLevelMapper.convertRequiredSkillToDTO(this.acquiredSkillService.createAcquiredSkillLevel(acquiredSkillId,
               AcquiredSkillLevelMapper.convertRequiredSkillToEntity(acquiredSkillLevel,modelMapper)),modelMapper);

    }
}
