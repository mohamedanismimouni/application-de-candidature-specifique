package com.talan.polaris.mapper;
import com.talan.polaris.dto.CollaboratorDTO;
import com.talan.polaris.dto.CollaboratorTeamDTO;
import com.talan.polaris.entities.CollaboratorEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * Mapping User Entity
 *
 * @author Imen mechergui
 * @since 1.0.0
 */
public class UserTeamMapper {

    private UserTeamMapper() {
    }

    /**
     *convert User DTO To Entity
     * @param userDTO
     * @param modelMapper
     * @return UserEntity
     */
    public static CollaboratorEntity convertUserDTOToEntity(CollaboratorTeamDTO userDTO, ModelMapper modelMapper) {
    /*    if (userDTO.getProfileType().equals(ProfileTypeEnum.COLLABORATOR)) {
          return modelMapper.map(userDTO, CollaboratorEntity.class);
        } else if (userDTO.getProfileType().equals(ProfileTypeEnum.MANAGER)) {
            return modelMapper.map(userDTO, ManagerEntity.class);
        } else if (userDTO.getProfileType().equals(ProfileTypeEnum.DIRECTOR)) {
            return modelMapper.map(userDTO, DirectorEntity.class);
        } else {
            return modelMapper.map(userDTO, HRResponsibleEntity.class);
        }*/
        return modelMapper.map(userDTO, CollaboratorEntity.class);
    }

    /**
     *convert User Entity To DTO
     * @param userEntity
     * @param modelMapper
     * @return UserDTO
     */
    public static CollaboratorTeamDTO convertUserEntityToDTO(CollaboratorEntity userEntity, ModelMapper modelMapper) {
        return modelMapper.map(userEntity, CollaboratorTeamDTO.class);

    }

    /**
     *convert User Entity List To DTO
     * @param userEntityList
     * @param modelMapper
     * @return  Collection<UserDTO>
     */
    public static Collection<CollaboratorDTO> convertUserEntityListToDTO(Collection<CollaboratorEntity> userEntityList, ModelMapper modelMapper) {
        Type userListType = new TypeToken<List<CollaboratorDTO>>() {
        }.getType();
        return modelMapper.map(userEntityList, userListType);

    }
}
