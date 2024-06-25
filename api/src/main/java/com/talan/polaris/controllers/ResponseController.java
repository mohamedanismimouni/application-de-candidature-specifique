package com.talan.polaris.controllers;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talan.polaris.enumerations.SurveyTypeEnum;
import com.talan.polaris.dto.AuthenticatedUser;
import com.talan.polaris.dto.Response;
import com.talan.polaris.dto.UserAuthenticationToken;
import com.talan.polaris.services.ResponseService;

import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;
import static com.talan.polaris.enumerations.ProfileTypeEnum.ProfileTypeConstants.COLLABORATOR_PROFILE_TYPE;

/**
 * A controller defining response resource endpoints.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/responses")
@Validated
public class ResponseController {

    private final ResponseService responseService;

    @Autowired
    public ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @GetMapping(params = { "collaboratorId", "surveyType" })
    public Collection<Response> getResponses(
            @RequestParam(value = "collaboratorId", required = true) Long collaboratorId,
            @RequestParam(value = "surveyType", required = true) @NotNull SurveyTypeEnum surveyType,
            @RequestParam(value = "evaluationId", required = false) String evaluationId) {

        return this.responseService.findResponsesByCollaboratorIdAndSurveyType(
                collaboratorId,
                surveyType,
                evaluationId);

    }

   /* @PostMapping()
    @PreAuthorize("hasRole('" + COLLABORATOR_PROFILE_TYPE + "')")
    public Response createResponse(
            @RequestBody @Valid Response response,
            Authentication authentication) {

        return this.responseService.createResponse(
                response,
                ((AuthenticatedUser) ((UserAuthenticationToken) authentication).getPrincipal()).getId());

    }*/

}
