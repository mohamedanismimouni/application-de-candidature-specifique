package com.talan.polaris.controllers;
import com.talan.polaris.dto.EvaluationDTO;
import com.talan.polaris.enumerations.EvaluationStatusEnum;
import com.talan.polaris.mapper.EvaluationMapper;
import com.talan.polaris.services.EvaluationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.json.JsonPatch;
import javax.validation.Valid;
import java.util.Collection;

import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;
/**
 * A controller defining evaluation resource endpoints.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/evaluations")
public class EvaluationController {

    private final EvaluationService evaluationService;
    private final ModelMapper modelMapper;

    @Autowired
    public EvaluationController(EvaluationService evaluationService, ModelMapper modelMapper) {
        this.evaluationService = evaluationService;
        this.modelMapper=modelMapper;

    }

    @GetMapping(params = { "collaboratorId" })
    @PreAuthorize("isManagerOfSameTeamAsCollaborator(#collaboratorId) OR "
            + "isSelf(#collaboratorId) OR "
            + "isSelf(#supervisorId)")
    public Collection<EvaluationDTO> getEvaluations(
            @RequestParam(value = "collaboratorId", required = true) Long collaboratorId,
            @RequestParam(value = "supervisorId", required = false) Long supervisorId,
            @RequestParam(value = "evaluationStatus", required = false) EvaluationStatusEnum evaluationStatus) {
        return EvaluationMapper.convertEvaluationEntityListToDTO(this.evaluationService.findEvaluations(collaboratorId, supervisorId, evaluationStatus),modelMapper);
    }

    @PostMapping()
    @PreAuthorize("canCreateEvaluation(#evaluation)")
    public EvaluationDTO createEvaluation(@RequestBody @Valid EvaluationDTO evaluation) {
        return EvaluationMapper.convertEvaluationEntityToDTO(this.evaluationService.createEvaluation(EvaluationMapper.convertEvaluationDTOToEntity(evaluation,modelMapper)),modelMapper);
    }

    @PatchMapping(path = "/{evaluationId}", consumes = "application/json-patch+json")
    @PreAuthorize("canUpdateEvaluation(#evaluationId)")
    public EvaluationDTO partialUpdateEvaluation(
            @PathVariable(value = "evaluationId", required = true) String evaluationId,
            @RequestBody JsonPatch jsonPatch) {
        return EvaluationMapper.convertEvaluationEntityToDTO(this.evaluationService.partialUpdateEvaluation(evaluationId, jsonPatch),modelMapper);

    }

}
