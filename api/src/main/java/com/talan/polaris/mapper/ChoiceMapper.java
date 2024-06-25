package com.talan.polaris.mapper;

import com.talan.polaris.dto.ChoiceDTO;
import com.talan.polaris.entities.ChoiceEntity;
import com.talan.polaris.entities.QuizChoiceEntity;
import com.talan.polaris.entities.RegularChoiceEntity;
import com.talan.polaris.enumerations.ChoiceTypeEnum;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * Mapping choice Entity
 *
 * @author Imen mechergui
 * @since 1.0.0
 */
public class ChoiceMapper {
    private ChoiceMapper() {

    }

    /**
     * convert Choice DTO To Entity
     *
     * @param choiceDTO
     * @param modelMapper
     * @return ChoiceEntity
     */
    public static ChoiceEntity convertChoiceDTOToEntity(ChoiceDTO choiceDTO, ModelMapper modelMapper) {
        if (choiceDTO.getChoiceType().equals(ChoiceTypeEnum.REGULAR)) {
            return modelMapper.map(choiceDTO, RegularChoiceEntity.class);

        } else {
            return modelMapper.map(choiceDTO, QuizChoiceEntity.class);
        }
    }

    /**
     * convert ChoiceDTO To Quiz Choice Entity
     *
     * @param choiceDTO
     * @param modelMapper
     * @return QuizChoiceEntity
     */
    public static QuizChoiceEntity convertChoiceDTOToQuizChoiceEntity(ChoiceDTO choiceDTO, ModelMapper modelMapper) {

        return modelMapper.map(choiceDTO, QuizChoiceEntity.class);

    }

    /**
     * convert ChoiceDTO To Regular Choice Entity
     *
     * @param choiceDTO
     * @param modelMapper
     * @return RegularChoiceEntity
     */
    public static RegularChoiceEntity convertChoiceDTOToRegularChoiceEntity(ChoiceDTO choiceDTO, ModelMapper modelMapper) {

        return modelMapper.map(choiceDTO, RegularChoiceEntity.class);

    }

    /**
     * convert Choice Entity To DTO
     *
     * @param choiceEntity
     * @param modelMapper
     * @return ChoiceDTO
     */
    public static ChoiceDTO convertChoiceEntityToDTO(ChoiceEntity choiceEntity, ModelMapper modelMapper) {
        return modelMapper.map(choiceEntity, ChoiceDTO.class);

    }

    /**
     * convert Choice Entity List To DTO
     *
     * @param choiceEntityList
     * @param modelMapper
     * @return Collection<ChoiceDTO>
     */
    public static Collection<ChoiceDTO> convertChoiceEntityListToDTO(Collection<ChoiceEntity> choiceEntityList, ModelMapper modelMapper) {
        Type choiceDTOListType = new TypeToken<List<ChoiceDTO>>() {
        }.getType();
        return modelMapper.map(choiceEntityList, choiceDTOListType);

    }

    /**
     * convert Quiz Choice Entity List To DTO
     *
     * @param choiceEntityList
     * @param modelMapper
     * @return Collection<ChoiceDTO>
     */
    public static Collection<ChoiceDTO> convertQuizChoiceEntityListToDTO(Collection<QuizChoiceEntity> choiceEntityList, ModelMapper modelMapper) {
        Type choiceDTOListType = new TypeToken<List<ChoiceDTO>>() {
        }.getType();
        return modelMapper.map(choiceEntityList, choiceDTOListType);

    }


}
