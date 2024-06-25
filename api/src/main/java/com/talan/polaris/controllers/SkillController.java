package com.talan.polaris.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.talan.polaris.dto.SkillDTO;
import com.talan.polaris.mapper.SkillMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talan.polaris.services.SkillService;
import com.talan.polaris.enumerations.SkillTypeEnum;

import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;
import static com.talan.polaris.enumerations.ProfileTypeEnum.ProfileTypeConstants.*;

/**
 * A controller defining skill resource endpoints.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/skills")
@Validated
public class SkillController {

    private final SkillService skillService;
    private final ModelMapper modelMapper;

    @Autowired
    public SkillController(SkillService skillService, ModelMapper modelMapper) {
        this.skillService = skillService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(params = { "skillType" })
    public Collection<SkillDTO> getSkills(
            @RequestParam(value = "skillType", required = true) @NotNull SkillTypeEnum skillType) {

        return this.skillService.findSkillsBySkillType(skillType)
                .stream()
                .map(skill-> SkillMapper.convertSkillToDTO(skill,modelMapper))
                .collect(Collectors.toList());
    }


    @PostMapping()
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "') OR "
                + "hasRole('" + MANAGER_PROFILE_TYPE + "')"
            + " OR hasRole('" + SUPPORT_PROFILE_TYPE + "')")
    public SkillDTO createSkill(@RequestBody @Valid SkillDTO skillDTO) {
        return SkillMapper.convertSkillToDTO(this.skillService.create(SkillMapper.convertSkillToEntity(skillDTO,modelMapper)),modelMapper);
    }

}
