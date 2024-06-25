package com.talan.polaris.mapper;



import com.talan.polaris.dto.ResponseTestDTO;
import com.talan.polaris.entities.ResponseTestEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface ResponseTestMapper {

    ResponseTestDTO toResponseDto(ResponseTestEntity response);


    List<ResponseTestDTO> toResponseDtos(List<ResponseTestEntity> responses);

    ResponseTestEntity toResponse(ResponseTestDTO responseDto);

}
