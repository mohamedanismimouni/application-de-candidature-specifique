/*
package com.talan.polaris.mapper;
import com.talan.polaris.dto.UserDTO;
import com.talan.polaris.entities.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

*/
/**
 * Mapping User Entity
 *
 * @author Imen mechergui
 * @since 1.0.0
 *//*

public class UserMapper {

    private UserMapper() {
    }

    */
/**
     *convert User DTO To Entity
     * @param userDTO
     * @param modelMapper
     * @return UserEntity
     *//*

    public static UserEntity convertUserDTOToEntity(UserDTO userDTO, ModelMapper modelMapper) {
   */
/*     if (userDTO.getProfileType().equals(ProfileTypeEnum.COLLABORATOR)) {
          return modelMapper.map(userDTO, CollaboratorEntity.class);
        } else if (userDTO.getProfileType().equals(ProfileTypeEnum.MANAGER)) {
            return modelMapper.map(userDTO, ManagerEntity.class);
        } else if (userDTO.getProfileType().equals(ProfileTypeEnum.DIRECTOR)) {
            return modelMapper.map(userDTO, DirectorEntity.class);
        } else {
            return modelMapper.map(userDTO, HRResponsibleEntity.class);
        }*//*

        return modelMapper.map(userDTO, UserEntity.class);
    }

    */
/**
     *convert User Entity To DTO
     * @param userEntity
     * @param modelMapper
     * @return UserDTO
     *//*

    public static UserDTO convertUserEntityToDTO(UserEntity userEntity, ModelMapper modelMapper) {
        return modelMapper.map(userEntity, UserDTO.class);

    }

    */
/**
     *convert User Entity List To DTO
     * @param userEntityList
     * @param modelMapper
     * @return  Collection<UserDTO>
     *//*

    public static Collection<UserDTO> convertUserEntityListToDTO(Collection<UserEntity> userEntityList, ModelMapper modelMapper) {
        Type userListType = new TypeToken<List<UserDTO>>() {
        }.getType();
        return modelMapper.map(userEntityList, userListType);

    }
}
*/
