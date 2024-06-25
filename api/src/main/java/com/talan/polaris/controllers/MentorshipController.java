package com.talan.polaris.controllers;

import com.talan.polaris.dto.MentorshipDTO;
import com.talan.polaris.enumerations.MentorshipStatusEnum;
import com.talan.polaris.mapper.MentorshipMapper;
import com.talan.polaris.services.MentorshipService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonPatch;
import javax.validation.Valid;
import java.util.Collection;

import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;

/**
 * A controller defining mentorship resource endpoints.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/mentorships")
public class MentorshipController {

    private final MentorshipService mentorshipService;
    private final ModelMapper modelMapper;

    @Autowired
    public MentorshipController(MentorshipService mentorshipService,ModelMapper modelMapper) {
        this.mentorshipService = mentorshipService;
        this.modelMapper=modelMapper;
    }

    @GetMapping(params = { "skillId", "careerPositionId" })
    public MentorshipDTO getMentorshipBySkillIdAndCareerPositionId(
            @RequestParam(value = "skillId", required = true) String skillId,
            @RequestParam(value = "careerPositionId", required = true) Long careerPositionId) {

        return MentorshipMapper.convertMentorshipEntityToDTO(this.mentorshipService.findMentorshipBySkillIdAndCareerPositionId(
                skillId,
                careerPositionId),modelMapper);

    }

    @GetMapping(params = { "mentorId", "menteeId" })
    public Collection<MentorshipDTO> getMentorshipsByMentorIdAndMenteeId(
            @RequestParam(value = "mentorId", required = true) Long mentorId,
            @RequestParam(value = "menteeId", required = true) Long menteeId,
            @RequestParam(value = "mentorshipStatus", required = false) MentorshipStatusEnum mentorshipStatus) {

        return MentorshipMapper.convertMentorshipEntityListToDTO( this.mentorshipService.findMentorshipsByMentorIdAndMenteeId(
                mentorId,
                menteeId,
                mentorshipStatus),modelMapper);

    }

    @GetMapping(params = { "mentorId" })
    public Collection<MentorshipDTO> getMentorshipsByMentorId(
            @RequestParam(value = "mentorId", required = true) Long mentorId) {

        return MentorshipMapper.convertMentorshipEntityListToDTO(this.mentorshipService.findMentorshipsByMentorId(mentorId),modelMapper);

    }

    @PostMapping()
    public MentorshipDTO createMentorship(@RequestBody @Valid MentorshipDTO mentorship) {
        return MentorshipMapper.convertMentorshipEntityToDTO(this.mentorshipService.create(MentorshipMapper.convertMentorshipDTOToEntity(mentorship,modelMapper)),modelMapper);
    }

    @PatchMapping(path = "/{mentorshipId}/mentor-evaluation", consumes = "application/json-patch+json")
    @PreAuthorize("isMentorOfMentorship(#mentorshipId)")
    public MentorshipDTO evaluateMentorshipForMentor(
            @PathVariable(value = "mentorshipId", required = true) Long mentorshipId,
            @RequestBody JsonPatch jsonPatch) {

        return MentorshipMapper.convertMentorshipEntityToDTO(this.mentorshipService.evaluateMentorshipForMentor(mentorshipId, jsonPatch),modelMapper);

    }

    @PatchMapping(path = "/{mentorshipId}/mentee-evaluation", consumes = "application/json-patch+json")
    @PreAuthorize("isMenteeOfMentorship(#mentorshipId)")
    public MentorshipDTO evaluateMentorshipForMentee(
                @PathVariable(value = "mentorshipId", required = true) Long mentorshipId,
            @RequestBody JsonPatch jsonPatch) {

        return MentorshipMapper.convertMentorshipEntityToDTO(this.mentorshipService.evaluateMentorshipForMentee(mentorshipId, jsonPatch),modelMapper);

    }

}
