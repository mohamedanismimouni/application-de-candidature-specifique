package com.talan.polaris.mapper;

import com.talan.polaris.dto.RequestStatusDTO;
import com.talan.polaris.entities.RequestStatusEntity;
import org.modelmapper.ModelMapper;

public class RequestStatusMapper {

     RequestStatusMapper() {
    }

    /**
     *
     * @param requestStatusDTO
     * @param modelMapper
     * @return RequestStatusEntity
     */
    public static RequestStatusEntity convertRequestStatusDTOToEntity(RequestStatusDTO requestStatusDTO, ModelMapper modelMapper) {
        return modelMapper.map(requestStatusDTO, RequestStatusEntity.class);
    }

    /**
     *
      * @param requestStatusEntity
     * @param modelMapper
     * @return RequestStatusDTO
     */
    public static RequestStatusDTO convertRequestStatusEntityToDTO(RequestStatusEntity requestStatusEntity, ModelMapper modelMapper) {
        return modelMapper.map(requestStatusEntity, RequestStatusDTO.class);
    }
}
