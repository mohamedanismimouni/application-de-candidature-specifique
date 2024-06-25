package com.talan.polaris.controllers;

import com.talan.polaris.dto.CareerPositionDTO;
import com.talan.polaris.entities.CareerPositionEntity;
import com.talan.polaris.enumerations.CareerPositionStatusEnum;
import com.talan.polaris.mapper.CareerPositionMapper;
import com.talan.polaris.services.CareerPositionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonPatch;
import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;

/**
 * A controller defining career position resource endpoints.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/career-positions")
public class CareerPositionController {

    private final CareerPositionService careerPositionService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public CareerPositionController(CareerPositionService careerPositionService) {
        this.careerPositionService = careerPositionService;
    }

    @GetMapping(params = {"collaboratorId"})
    public Collection<CareerPositionDTO> getCareerPositionsByCollaboratorId(
            @RequestParam(value = "collaboratorId", required = true) Long collaboratorId,
            @RequestParam(value = "status", required = false) CareerPositionStatusEnum status) {

        return this.careerPositionService.findCareerPositionsByCollaboratorIdAndStatus(collaboratorId, status)
                .stream()
                .map(carrer -> CareerPositionMapper.convertCareerPostionToDTO(carrer, modelMapper))
                .collect(Collectors.toList());
    }

    @PostMapping()
    public CareerPositionDTO createCareerPosition(
            @RequestBody @Valid CareerPositionDTO careerPositionDTO) {

        return CareerPositionMapper.convertCareerPostionToDTO(
                this.careerPositionService.createCareerPosition(CareerPositionMapper.convertCareerPositionToEntity(careerPositionDTO, modelMapper)),modelMapper);

    }

    @PostMapping(path="/update")
    public CareerPositionDTO updateCareerPosition(
            @RequestBody @Valid CareerPositionDTO careerPositionDTO) {
        return CareerPositionMapper.convertCareerPostionToDTO(
                this.careerPositionService.create(CareerPositionMapper.convertCareerPositionToEntity(careerPositionDTO, modelMapper)),modelMapper);

    }
    @PatchMapping(path = "/{careerPositionId}", consumes = "application/json-patch+json")
    @PreAuthorize("canUpdateCareerPosition(#careerPositionId)")
    public CareerPositionEntity partialUpdateCareerPosition(
            @PathVariable(value = "careerPositionId", required = true) Long careerPositionId,
            @RequestBody JsonPatch jsonPatch) {
        return this.careerPositionService.partialUpdateCareerPosition(careerPositionId, jsonPatch);

    }

}
