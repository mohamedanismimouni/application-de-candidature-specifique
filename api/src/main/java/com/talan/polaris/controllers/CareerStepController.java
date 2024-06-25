package com.talan.polaris.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.talan.polaris.dto.CareerStepDTO;

import com.talan.polaris.mapper.CareerStepMapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.talan.polaris.services.CareerStepService;

import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;
import static com.talan.polaris.enumerations.ProfileTypeEnum.ProfileTypeConstants.HR_RESPONSIBLE_PROFILE_TYPE;
import static com.talan.polaris.enumerations.ProfileTypeEnum.ProfileTypeConstants.SUPPORT_PROFILE_TYPE;

/**
 * A controller defining career step resource endpoints.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/career-steps")
public class CareerStepController {

    private final CareerStepService careerStepService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public CareerStepController(CareerStepService careerStepService) {
        this.careerStepService = careerStepService;
    }

    @GetMapping()
    public Collection<CareerStepDTO> getCareerSteps() {
        return this.careerStepService.findAll()
                .stream()
                .map(carrer -> CareerStepMapper.convertCareerStepToDTO(carrer, modelMapper))
                .collect(Collectors.toList());
    }

    @GetMapping(params = {"teamId"})
    public Collection<CareerStepDTO> getCareerStepsAssociatedToProfilesWithTeamId(
            @RequestParam(value = "teamId", required = true) String teamId) {
        return this.careerStepService.findCareerStepsAssociatedToProfilesWithTeamId(teamId)
                .stream()
                .map(carrer -> CareerStepMapper.convertCareerStepToDTO(carrer, modelMapper))
                .collect(Collectors.toList());
    }

    @PostMapping()
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "') OR hasRole('" + SUPPORT_PROFILE_TYPE + "')")
    public CareerStepDTO createCareerStep(@RequestBody @Valid CareerStepDTO careerStepDTO) {

        return CareerStepMapper.convertCareerStepToDTO(this.careerStepService.create(CareerStepMapper.convertCareerStepToEntity(careerStepDTO, modelMapper)), modelMapper);
    }

    @DeleteMapping(path = "/{careerStepId}")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "') OR hasRole('" + SUPPORT_PROFILE_TYPE + "')")
    public void deleteCareerStep(@PathVariable(value = "careerStepId", required = true) String careerStepId) {
        this.careerStepService.deleteCareerStepById(careerStepId);
    }

}
