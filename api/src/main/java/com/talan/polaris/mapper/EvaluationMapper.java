package com.talan.polaris.mapper;

import com.talan.polaris.dto.EvaluationDTO;
import com.talan.polaris.entities.CareerPositionEntity;
import com.talan.polaris.entities.EvaluationEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * Mapping Evaluation Entity
 *
 * @author Imen mechergui
 * @since 1.0.0
 */
public class EvaluationMapper {
    private EvaluationMapper() {

    }

    /**
     * convert Evaluation DTO To Entity
     *
     * @param evaluationDTO
     * @param modelMapper
     * @return EvaluationEntity
     */
    public static EvaluationEntity convertEvaluationDTOToEntity(EvaluationDTO evaluationDTO, ModelMapper modelMapper) {
        CareerPositionEntity  career=CareerPositionMapper.convertCareerPositionToEntity(evaluationDTO.getCareerPosition(),modelMapper);
        EvaluationEntity evaluation=new EvaluationEntity();
        evaluation.setCareerPosition(career);
        evaluation.setEvaluationDate(evaluationDTO.getEvaluationDate());
        return evaluation;
    }

    /**
     * convert Evaluation Entity To DTO
     *
     * @param evaluationEntity
     * @param modelMapper
     * @return EvaluationDTO
     */
    public static EvaluationDTO convertEvaluationEntityToDTO(EvaluationEntity evaluationEntity, ModelMapper modelMapper) {
        return modelMapper.map(evaluationEntity, EvaluationDTO.class);

    }

    /**
     * convert Evaluation Entity List To DTO
     *
     * @param evaluationEntityList
     * @param modelMapper
     * @return Collection<EvaluationDTO>
     */

    public static Collection<EvaluationDTO> convertEvaluationEntityListToDTO(Collection<EvaluationEntity> evaluationEntityList, ModelMapper modelMapper) {
        Type evaluationListType = new TypeToken<List<EvaluationDTO>>() {
        }.getType();
        return modelMapper.map(evaluationEntityList, evaluationListType);

    }
}
