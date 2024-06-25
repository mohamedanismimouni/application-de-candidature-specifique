package com.talan.polaris.mapper;

import com.talan.polaris.dto.CivilityDTO;
import com.talan.polaris.entities.CivilityEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class CivilityMapper {

    CivilityMapper(){
    }

    /**
     *
     * @param civilityDTO
     * @param modelMapper
     * @return CivilityEntity
     */
    public static CivilityEntity convertCivilityDTOToEntity(CivilityDTO civilityDTO, ModelMapper modelMapper) {
        return modelMapper.map(civilityDTO, CivilityEntity.class);
    }

    /**
     *
     * @param civilityEntity
     * @param modelMapper
     * @return CivilityDTO
     */
    public static CivilityDTO convertCivilityEntityToDTO(CivilityEntity civilityEntity, ModelMapper modelMapper) {
        return modelMapper.map(civilityEntity, CivilityDTO.class);
    }


    /**
     *convert civility Entity List To DTO
     * @param civilityEntityList
     * @param modelMapper
     * @return Collection<CivilityDTO>
     */
    public static Collection<CivilityDTO> convertCivilityListToDTO(Collection<CivilityEntity> civilityEntityList, ModelMapper modelMapper) {
        Type civilityList = new TypeToken<List<CivilityDTO>>() {
        }.getType();
        return modelMapper.map(civilityEntityList, civilityList);

    }

}
