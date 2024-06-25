package com.talan.polaris.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.talan.polaris.dto.CareerPathDTO;
import com.talan.polaris.mapper.CarrerPathMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talan.polaris.services.CareerPathService;

import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;
import static com.talan.polaris.enumerations.ProfileTypeEnum.ProfileTypeConstants.HR_RESPONSIBLE_PROFILE_TYPE;
import static com.talan.polaris.enumerations.ProfileTypeEnum.ProfileTypeConstants.SUPPORT_PROFILE_TYPE;

/**
 * A controller defining career path resource endpoints.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/career-paths")
public class CareerPathController {

    private final CareerPathService careerPathService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public CareerPathController(CareerPathService careerPathService) {
        this.careerPathService = careerPathService;
    }

    @GetMapping()
    public Collection<CareerPathDTO> getCareerPaths() {
        return this.careerPathService.findAll()
                .stream()
                .map(carrer -> CarrerPathMapper.convertCareerPathToDTO(carrer, modelMapper))
                .collect(Collectors.toList());
    }

    @PostMapping()
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "') OR hasRole('" + SUPPORT_PROFILE_TYPE + "')")
    public CareerPathDTO createCareerPath(@RequestBody @Valid CareerPathDTO careerPathDTO) {
        return CarrerPathMapper.convertCareerPathToDTO(
                this.careerPathService.createCareerPath(CarrerPathMapper.convertCareerPathToEntity(careerPathDTO, modelMapper)), modelMapper);
    }

    @DeleteMapping(path = "/{careerPathId}")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "') OR hasRole('" + SUPPORT_PROFILE_TYPE + "')")
    public void deleteCareerPath(@PathVariable(value = "careerPathId", required = true) String careerPathId) {
        this.careerPathService.deleteById(careerPathId);
    }


}
