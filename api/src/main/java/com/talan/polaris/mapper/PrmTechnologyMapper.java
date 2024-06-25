package com.talan.polaris.mapper;


import com.talan.polaris.dto.PrmTechnologyDTO;
import com.talan.polaris.entities.PrmTechnologyEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface PrmTechnologyMapper {
    PrmTechnologyDTO toPrmTechnologyDto(PrmTechnologyEntity prmTechnology);

     List<PrmTechnologyDTO> toPrmTechnologyDtos(List<PrmTechnologyEntity> prmTechnologies);

    PrmTechnologyEntity toPrmTechnology(PrmTechnologyDTO prmTechnologyDto);
}


