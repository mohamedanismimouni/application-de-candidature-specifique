package com.talan.polaris.controllers;

import com.talan.polaris.dto.ProfileDTO;
import com.talan.polaris.mapper.ProfileMapper;
import com.talan.polaris.services.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;
import static com.talan.polaris.enumerations.ProfileTypeEnum.ProfileTypeConstants.MANAGER_PROFILE_TYPE;
/**
 * A controller defining profile resource endpoints.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/profiles")
public class ProfileController {

    private final ProfileService profileService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProfileController(ProfileService profileService, ModelMapper modelMapper) {
        this.profileService = profileService;
        this.modelMapper = modelMapper;

    }

    @GetMapping(params = {"teamId", "initializedOnly"})
    public Collection<ProfileDTO> getProfiles(
            @RequestParam(value = "teamId", required = true) String teamId,
            @RequestParam(value = "initializedOnly", required = true) boolean initializedOnly) {
        return ProfileMapper.convertProfileEntityListToDTO(this.profileService.findProfilesByTeamId(teamId, initializedOnly), modelMapper);

    }

    @GetMapping(params = {"teamId", "careerStepId"})
    public Collection<ProfileDTO> getProfiles(
            @RequestParam(value = "teamId", required = true) String teamId,
            @RequestParam(value = "careerStepId", required = true) String careerStepId) {
         return ProfileMapper.convertProfileEntityListToDTO(this.profileService.findProfilesByTeamIdAndCareerStepId(teamId, careerStepId), modelMapper);

    }

    @PostMapping()
    @PreAuthorize("hasRole('" + MANAGER_PROFILE_TYPE + "')")
    public ProfileDTO createProfile(@RequestBody @Valid ProfileDTO profile) {
        return ProfileMapper.convertProfileEntityToDTO(this.profileService.createProfile(ProfileMapper.convertProfileDTOToEntity(profile, modelMapper)), modelMapper);
    }

}
