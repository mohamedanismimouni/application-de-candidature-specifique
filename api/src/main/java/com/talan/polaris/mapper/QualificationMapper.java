package com.talan.polaris.mapper;

import com.talan.polaris.dto.QualificationDTO;
import com.talan.polaris.entities.QualificationEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class QualificationMapper {

    QualificationMapper(){
    }

    /**
     *
     * @param qualificationDTO
     * @param modelMapper
     * @return QualificationEntity
     */
    public static QualificationEntity convertQualificationDTOToEntity(QualificationDTO qualificationDTO, ModelMapper modelMapper) {
        return modelMapper.map(qualificationDTO, QualificationEntity.class);
    }

    /**
     *
     * @param qualificationEntity
     * @param modelMapper
     * @return QualificationDTO
     */
    public static QualificationDTO convertQualificationEntityToDTO(QualificationEntity qualificationEntity, ModelMapper modelMapper) {
        return modelMapper.map(qualificationEntity, QualificationDTO.class);
    }


    /**
     *convert Qualification  Entity List To DTO
     * @param qualificationEntityList
     * @param modelMapper
     * @return Collection<QualificationDTO>
     */
    public static Collection<QualificationDTO> convertQualificationListToDTO(Collection<QualificationEntity> qualificationEntityList, ModelMapper modelMapper) {
        Type qualificationList = new TypeToken<List<QualificationDTO>>() {
        }.getType();
        return modelMapper.map(qualificationEntityList, qualificationList);

    }

}
