package com.talan.polaris.controllers;

import com.talan.polaris.dto.TeamDTO;
import com.talan.polaris.mapper.TeamMapper;
import com.talan.polaris.services.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonPatch;
import javax.validation.Valid;
import java.util.Collection;

import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;
import static com.talan.polaris.enumerations.ProfileTypeEnum.ProfileTypeConstants.HR_RESPONSIBLE_PROFILE_TYPE;
import static com.talan.polaris.enumerations.ProfileTypeEnum.ProfileTypeConstants.SUPPORT_PROFILE_TYPE;

/**
 * A controller defining team resource endpoints.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/teams")
public class TeamController {

    private static final String[] DEFAULT_TEAM_ENTITY_SORT_FIELDS_NAMES = { "name" };

    private final TeamService teamService;
    private final ModelMapper modelMapper;

    @Autowired
    public TeamController(TeamService teamService, ModelMapper modelMapper) {
        this.teamService = teamService;
        this.modelMapper=modelMapper;
    }

    @GetMapping(params = { "managedBy" })
    public TeamDTO getTeamByManagerId(
            @RequestParam(value = "managedBy", required = true) Long managerId) {

        return TeamMapper.convertTeamEntityToDTO(this.teamService.findTeamByManagerId(managerId),modelMapper);

    }

    @GetMapping()
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "') OR hasRole('" + SUPPORT_PROFILE_TYPE + "')")
    public Collection<TeamDTO> getTeams() {

        return TeamMapper.convertTeamEntityListToDTO(this.teamService.findAll(Sort.by(
                Sort.Direction.ASC,
                DEFAULT_TEAM_ENTITY_SORT_FIELDS_NAMES)),modelMapper);

    }

    @PostMapping()
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "') OR hasRole('" + SUPPORT_PROFILE_TYPE + "')")
    public TeamDTO createTeam(@RequestBody @Valid TeamDTO team) {

        return TeamMapper.convertTeamEntityToDTO(this.teamService.createTeam(TeamMapper.convertTeamDTOToEntity(team,modelMapper)),modelMapper);
    }

    @PatchMapping(path = "/{teamId}", consumes = "application/json-patch+json")
    @PreAuthorize("canUpdateTeam(#teamId)")
    public TeamDTO partialUpdateTeam(
            @PathVariable(value = "teamId", required = true) String teamId,
            @RequestBody JsonPatch jsonPatch) {

        return  TeamMapper.convertTeamEntityToDTO( this.teamService.partialUpdateTeam(teamId, jsonPatch),modelMapper);

    }

    @PostMapping(path = "/team-name/{teamId}")
    @PreAuthorize("hasRole('" + HR_RESPONSIBLE_PROFILE_TYPE + "') OR hasRole('" + SUPPORT_PROFILE_TYPE + "')")
    public void updateTeamName(@PathVariable(value = "teamId", required = true) Long managerTeamId, @RequestBody @Valid String name) {
        this.teamService.updateTeamName(managerTeamId, name);
    }

}
