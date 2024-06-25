package com.talan.polaris.controllers;

import com.talan.polaris.dto.OnboardingDTO;
import com.talan.polaris.dto.OnboardingHint;
import com.talan.polaris.mapper.OnboardingMapper;
import com.talan.polaris.services.OnboardingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.json.JsonPatch;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;

/**
 * A controller defining onboarding resource endpoints.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/onboardings")
public class OnboardingController {

    private final OnboardingService onboardingService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public OnboardingController(OnboardingService onboardingService) {
        this.onboardingService = onboardingService;
    }

    @GetMapping(params = { "freshRecruitId" })
    @PreAuthorize("isManagerOfSameTeamAsCollaborator(#freshRecruitId)")
    public Collection<OnboardingDTO> getOnboardings(
            @RequestParam(value = "freshRecruitId", required = true) Long freshRecruitId) {

        return this.onboardingService.findOnboardingsByFreshRecruitId(freshRecruitId).stream()
                .map(onboarding -> OnboardingMapper.convertOnboardingToDTO(onboarding, modelMapper))
                .collect(Collectors.toList());

    }

    @GetMapping(params = { "secretWordPartHolderId", "freshRecruitId" })
    @PreAuthorize("isSelfLong(#secretWordPartHolderId)")
    public OnboardingDTO getOnboarding(
            @RequestParam(value = "secretWordPartHolderId", required = true) Long secretWordPartHolderId,
            @RequestParam(value = "freshRecruitId", required = true) Long freshRecruitId) {

        return OnboardingMapper.convertOnboardingToDTO(this.onboardingService.findOnboardingBySecretWordPartHolderIdAndFreshRecruitId(
                secretWordPartHolderId,
                freshRecruitId),modelMapper);

    }

    @GetMapping(path = "/hint", params = { "freshRecruitId" })
    @PreAuthorize("isSelfLong(#freshRecruitId)")
    public OnboardingHint getOnboardingHint(
            @RequestParam(value = "freshRecruitId", required = true) Long freshRecruitId) {

        return this.onboardingService.getOnboardingHint(freshRecruitId);

    }

    @PatchMapping(path = "/{onboardingId}/rating", consumes = "application/json-patch+json")
    @PreAuthorize("isSecretWordPartHolder(#onboardingId)")
    public OnboardingDTO rateOnboarding(
            @PathVariable(value = "onboardingId", required = true) String onboardingId,
            @RequestBody JsonPatch jsonPatch) {

        return OnboardingMapper.convertOnboardingToDTO(this.onboardingService.rateOnboarding(onboardingId, jsonPatch),modelMapper);

    }

}
