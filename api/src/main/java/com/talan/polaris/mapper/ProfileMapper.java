package com.talan.polaris.mapper;

import com.talan.polaris.dto.ProfileDTO;
import com.talan.polaris.entities.ProfileEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * Mapping Profile Entity
 *
 * @author Imen mechergui
 * @since 1.0.0
 */
public class ProfileMapper {
    private ProfileMapper() {

    }

    /**
     * convert Profil eDTO To Entity
     *
     * @param profileDTO
     * @param modelMapper
     * @return ProfileEntity
     */
    public static ProfileEntity convertProfileDTOToEntity(ProfileDTO profileDTO, ModelMapper modelMapper) {

        return modelMapper.map(profileDTO, ProfileEntity.class);
    }

    /**
     * convert Profile Entity To DTO
     *
     * @param profileEntity
     * @param modelMapper
     * @return ProfileDTO
     */
    public static ProfileDTO convertProfileEntityToDTO(ProfileEntity profileEntity, ModelMapper modelMapper) {
        return modelMapper.map(profileEntity, ProfileDTO.class);

    }

    /**
     * convert Profile Entity List To DTO
     *
     * @param profileEntityList
     * @param modelMapper
     * @return Collection<ProfileDTO>
     */
    public static Collection<ProfileDTO> convertProfileEntityListToDTO(Collection<ProfileEntity> profileEntityList, ModelMapper modelMapper) {
        Type profileListType = new TypeToken<List<ProfileDTO>>() {
        }.getType();
        return modelMapper.map(profileEntityList, profileListType);

    }
}
