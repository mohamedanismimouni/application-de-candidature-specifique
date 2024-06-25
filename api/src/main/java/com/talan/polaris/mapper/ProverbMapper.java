package com.talan.polaris.mapper;

import com.talan.polaris.dto.ProverbDTO;
import com.talan.polaris.entities.ProverbEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * Mapping Proverb Entity
 *
 * @author Imen mechergui
 * @since 2.0.0
 */
public class ProverbMapper {
    private ProverbMapper() {

    }

    /**
     *convert proverb DTO To Entity
     * @param proverbDTO
     * @param modelMapper
     * @return ProverbEntity
     */
    public static ProverbEntity convertProverbDTOToEntity(ProverbDTO proverbDTO, ModelMapper modelMapper) {

        return modelMapper.map(proverbDTO, ProverbEntity.class);
    }

    /**
     *convert Proverb Entity To DTO
     * @param proverbEntity
     * @param modelMapper
     * @return ProverbDTO
     */
    public static ProverbDTO convertProverbEntityToDTO(ProverbEntity proverbEntity, ModelMapper modelMapper) {
        return modelMapper.map(proverbEntity, ProverbDTO.class);

    }

    /**
     *convert Proverb Entity List To DTO
     * @param proverbEntityList
     * @param modelMapper
     * @return Collection<ProverbDTO>
     */
    public static Collection<ProverbDTO> convertProverbEntityListToDTO(Collection<ProverbEntity> proverbEntityList, ModelMapper modelMapper) {
        Type proverbListType = new TypeToken<List<ProverbDTO>>() {
        }.getType();
        return modelMapper.map(proverbEntityList, proverbListType);

    }
}
