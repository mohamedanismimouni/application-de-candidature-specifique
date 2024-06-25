package com.talan.polaris.mapper;

import com.talan.polaris.dto.UsefulLinkDTO;
import com.talan.polaris.entities.UsefulLinkEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * Mapping Useful Link
 *
 * @author Imen mechergui
 * @since 2.0.0
 */
public class UsefulLinkMapper {
    private UsefulLinkMapper() {

    }

    /**
     *convert UsefulLink DTO To Entity
     * @param usefulLinkDTO
     * @param modelMapper
     * @return UsefulLinkEntity
     */
    public static UsefulLinkEntity convertUsefulLinkDTOToEntity(UsefulLinkDTO usefulLinkDTO, ModelMapper modelMapper) {

        return modelMapper.map(usefulLinkDTO, UsefulLinkEntity.class);
    }

    /**
     *convert UsefulLink Entity To DTO
     * @param usefulLinkEntity
     * @param modelMapper
     * @return UsefulLinkDTO
     */
    public static UsefulLinkDTO convertUsefulLinkEntityToDTO(UsefulLinkEntity usefulLinkEntity, ModelMapper modelMapper) {
        return modelMapper.map(usefulLinkEntity, UsefulLinkDTO.class);

    }

    /**
     *convert UsefulLink Entity List To DTO
     * @param usefulLinkEntityList
     * @param modelMapper
     * @return Collection<UsefulLinkDTO>
     */
    public static Collection<UsefulLinkDTO> convertUsefulLinkEntityListToDTO(Collection<UsefulLinkEntity> usefulLinkEntityList, ModelMapper modelMapper) {
        Type usefulLinkListType = new TypeToken<List<UsefulLinkDTO>>() {
        }.getType();
        return modelMapper.map(usefulLinkEntityList, usefulLinkListType);

    }
}
