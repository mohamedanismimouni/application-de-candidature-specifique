package com.talan.polaris.mapper;

import com.talan.polaris.dto.UsefulDocumentDTO;
import com.talan.polaris.entities.UsefulDocumentEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class UsefulDocumentMapper {
    UsefulDocumentMapper(){}

    /**
     *
     * @param usefulDocumentDTO
     * @param modelMapper
     * @return UsefulDocumentEntity
     */
    public static UsefulDocumentEntity convertUsefulDocumentDTOToEntity(UsefulDocumentDTO usefulDocumentDTO, ModelMapper modelMapper) {
        return modelMapper.map(usefulDocumentDTO, UsefulDocumentEntity.class);
    }

    /**
     *
     * @param usefulDocumentEntity
     * @param modelMapper
     * @return UsefulDocumentDTO
     */
    public static UsefulDocumentDTO convertUsefulDocumentEntityToDTO(UsefulDocumentEntity usefulDocumentEntity, ModelMapper modelMapper) {
        return modelMapper.map(usefulDocumentEntity, UsefulDocumentDTO.class);
    }



    /**
     *convert request Type Entity List To DTO
     * @param usefulDocumentEntityList
     * @param modelMapper
     * @return Collection<RequestTypeDTO>
     */
    public static Collection<UsefulDocumentDTO> convertUsefulDocumentEntityListToDTO(Collection<UsefulDocumentEntity> usefulDocumentEntityList, ModelMapper modelMapper) {
        Type usefulDocumentListType = new TypeToken<List<UsefulDocumentDTO>>() {}.getType();
        return modelMapper.map(usefulDocumentEntityList, usefulDocumentListType);

    }
}
