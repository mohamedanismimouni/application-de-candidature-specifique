package com.talan.polaris.mapper;


import com.talan.polaris.dto.ResultTestDTO;
import com.talan.polaris.entities.ResultTestEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface ResultTestMapper {

    ResultTestDTO toResultDto(ResultTestEntity result);

    List<ResultTestDTO> toResultDtos(List<ResultTestEntity> results);

    ResultTestEntity toResult(ResultTestDTO resultDto);

}
