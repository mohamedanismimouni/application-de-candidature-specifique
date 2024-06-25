package com.talan.polaris.controllers;

import com.talan.polaris.services.ScoreService;
import com.talan.polaris.services.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;
import static com.talan.polaris.enumerations.ProfileTypeEnum.ProfileTypeConstants.HR_RESPONSIBLE_PROFILE_TYPE;

@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/score")
public class ScoreController {
    private final ScoreService scoreService;
    private final ModelMapper modelMapper;

    @Autowired
    public ScoreController(ScoreService scoreService, ModelMapper modelMapper) {
        this.scoreService = scoreService;
        this.modelMapper=modelMapper;
    }

    @GetMapping(path = "/{email}")
    public Integer getScore(@PathVariable(value = "email", required = true)  String email) {
        return this.scoreService.getScoreByUserEmail(email);
    }
}
