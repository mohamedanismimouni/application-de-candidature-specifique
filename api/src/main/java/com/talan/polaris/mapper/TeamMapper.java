package com.talan.polaris.mapper;

import com.talan.polaris.dto.TeamDTO;
import com.talan.polaris.entities.TeamEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
/**
 * Mapping Team Entity
 *
 * @author Imen mechergui
 * @since 1.0.0
 */
public class TeamMapper {
    private TeamMapper() {

    }

    /**
     *convert Team DTO To Entity
     * @param teamDTO
     * @param modelMapper
     * @return TeamEntity
     */
    public static TeamEntity convertTeamDTOToEntity(TeamDTO teamDTO, ModelMapper modelMapper) {

        return modelMapper.map(teamDTO, TeamEntity.class);
    }

    /**
     *convert Team Entity To DTO
     * @param teamEntity
     * @param modelMapper
     * @return TeamDTO
     */
    public static TeamDTO convertTeamEntityToDTO(TeamEntity teamEntity, ModelMapper modelMapper) {
        return modelMapper.map(teamEntity, TeamDTO.class);

    }

    /**
     *convert Team Entity List To DTO
     * @param teamEntityList
     * @param modelMapper
     * @return Collection<TeamDTO>
     */
    public static Collection<TeamDTO> convertTeamEntityListToDTO(Collection<TeamEntity> teamEntityList, ModelMapper modelMapper) {
        Type teamListType = new TypeToken<List<TeamDTO>>() {
        }.getType();
        return modelMapper.map(teamEntityList, teamListType);

    }
}
