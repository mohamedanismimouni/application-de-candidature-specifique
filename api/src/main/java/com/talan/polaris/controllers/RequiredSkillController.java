package com.talan.polaris.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.talan.polaris.dto.RequiredSkillDTO;
import com.talan.polaris.mapper.RequiredSkillMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talan.polaris.enumerations.SkillTypeEnum;
import com.talan.polaris.services.RequiredSkillService;

import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;
import static com.talan.polaris.enumerations.ProfileTypeEnum.ProfileTypeConstants.MANAGER_PROFILE_TYPE;

/**
 * A controller defining required skill resource endpoints.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/required-skills")
public class RequiredSkillController {

    private final RequiredSkillService requiredSkillService;
    private final ModelMapper modelMapper;

    @Autowired
    public RequiredSkillController(RequiredSkillService requiredSkillService, ModelMapper modelMapper) {
        this.requiredSkillService = requiredSkillService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(params = { "profileId" })
    public Collection<RequiredSkillDTO> getRequiredSkills(
            @RequestParam(value = "profileId", required = true) String profileId,
            @RequestParam(value = "skillType", required = false) SkillTypeEnum skillType) {

        return this.requiredSkillService.findRequiredSkillsByProfileIdAndSkillType(profileId, skillType)
                .stream()
                .map(requiredSkill -> RequiredSkillMapper.convertRequiredSkillToDTO(requiredSkill,modelMapper))
                .collect(Collectors.toList());

    }

    @PostMapping()
    @PreAuthorize("hasRole('" + MANAGER_PROFILE_TYPE + "')")
    public RequiredSkillDTO createRequiredSkill(
            @RequestBody @Valid RequiredSkillDTO requiredDTO) {

        return RequiredSkillMapper.convertRequiredSkillToDTO(this.requiredSkillService.create(RequiredSkillMapper.convertRequiredSkillToEntity(requiredDTO,modelMapper)),modelMapper);

    }

}
