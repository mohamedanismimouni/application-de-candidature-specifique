package com.talan.polaris.controllers;

import com.talan.polaris.dto.ChoiceDTO;
import com.talan.polaris.dto.QuestionDTO;
import com.talan.polaris.enumerations.SurveyTypeEnum;
import com.talan.polaris.mapper.ChoiceMapper;
import com.talan.polaris.mapper.QuestionMapper;
import com.talan.polaris.services.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;
import static com.talan.polaris.enumerations.ProfileTypeEnum.ProfileTypeConstants.HR_RESPONSIBLE_PROFILE_TYPE;
/**
 * A controller defining question resource endpoints.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/questions")
@Validated
public class QuestionController {

    private final QuestionService questionService;
    private final ModelMapper modelMapper;

    @Autowired
    public QuestionController(QuestionService questionService, ModelMapper modelMapper) {
        this.questionService = questionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(params = {"surveyType", "enabled"})
    public Collection<QuestionDTO> getQuestions(
            @RequestParam(value = "surveyType", required = true) @NotNull SurveyTypeEnum surveyType,
            @RequestParam(value = "enabled", required = false) Optional<Boolean> enabled) {
        return QuestionMapper.convertQuestionEntityListToDTO(this.questionService.findQuestionsBySurveyTypeAndEnabledStatus(surveyType, enabled), modelMapper);

    }

    @GetMapping(path = "/{questionId}/choices", params = {"valid"})
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "') OR "
            + "hasRespondedToQuestion(#questionId)")
    public Collection<ChoiceDTO> getQuizChoicesByValidStatus(
            @PathVariable(value = "questionId", required = true) String questionId,
            @RequestParam(value = "valid", required = true) boolean valid) {
        return ChoiceMapper.convertQuizChoiceEntityListToDTO(this.questionService.findQuizChoicesByValidStatus(questionId, valid),modelMapper);
    }

    @PostMapping()
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "')")
    public QuestionDTO createQuestion(@RequestBody @Valid QuestionDTO questionDTO) {       
         
    	return QuestionMapper.convertQuestionEntityToDTO(this.questionService.createQuestion(QuestionMapper.convertQuestionAndChoicesDTOToEntity(questionDTO,modelMapper)), modelMapper);
    }

    @PostMapping(path = "/{questionId}/choices")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "')")
    public ChoiceDTO createChoice(
            @PathVariable(value = "questionId", required = true) String questionId,
            @RequestBody @Valid ChoiceDTO choiceDTO) {
        return ChoiceMapper.convertChoiceEntityToDTO(this.questionService.createChoice(questionId, ChoiceMapper.convertChoiceDTOToEntity(choiceDTO, modelMapper)), modelMapper);
    }

    @DeleteMapping(path = "/{questionId}")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "')")
    public void deleteQuestion(@PathVariable(value = "questionId", required = true) String questionId) {
        this.questionService.deleteQuestion(questionId);
    }

    @DeleteMapping(path = "/{questionId}/choices/{choiceId}")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "')")
    public void deleteChoice(
            @PathVariable(value = "questionId", required = true) String questionId,
            @PathVariable(value = "choiceId", required = true) String choiceId) {
        this.questionService.deleteChoice(questionId, choiceId);
    }

}
