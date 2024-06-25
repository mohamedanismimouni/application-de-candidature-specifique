package com.talan.polaris.mapper;

import com.talan.polaris.dto.CollaboratorDTO;
import com.talan.polaris.dto.CollaboratorTeamDTO;
import com.talan.polaris.dto.CollaboratorUserDTO;
import com.talan.polaris.entities.CollaboratorEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class CollaboratorMapper {

    CollaboratorMapper(){
    }

    /**
     *
     * @param collaboratorDTO
     * @param modelMapper
     * @return CollabEntity
     */
    public static CollaboratorEntity convertCollabDTOToEntity(CollaboratorDTO collaboratorDTO, ModelMapper modelMapper) {
        return modelMapper.map(collaboratorDTO, CollaboratorEntity.class);
    }

    /**
     *
     * @param collaboratorEntity
     * @param modelMapper
     * @return CollabDTO
     */
    public static CollaboratorDTO convertCollabEntityToDTO(CollaboratorEntity collaboratorEntity, ModelMapper modelMapper) {
        return modelMapper.map(collaboratorEntity, CollaboratorDTO.class);
    }

    public static CollaboratorTeamDTO convertCollabTeamEntityToDTO(CollaboratorEntity collaboratorEntity, ModelMapper modelMapper) {
        return modelMapper.map(collaboratorEntity, CollaboratorTeamDTO.class);
    }

    /**
     *convert collab  Entity List To DTO
     * @param collaboratorEntityList
     * @param modelMapper
     * @return Collection<CollabDTO>
     */
    public static Collection<CollaboratorDTO> convertCollabListToDTO(Collection<CollaboratorEntity> collaboratorEntityList, ModelMapper modelMapper) {
        Type collabListType = new TypeToken<List<CollaboratorDTO>>() {
        }.getType();
        return modelMapper.map(collaboratorEntityList, collabListType);

    }

    /**
     *
     * @param collaboratorEntity
     * @param modelMapper
     * @return CollabUserDTO
     */
    public static CollaboratorUserDTO convertCollabEntityToCollabUserDTO(CollaboratorEntity collaboratorEntity, ModelMapper modelMapper) {
        return modelMapper.map(collaboratorEntity, CollaboratorUserDTO.class);
    }

}
