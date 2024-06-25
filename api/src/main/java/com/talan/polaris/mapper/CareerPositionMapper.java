package com.talan.polaris.mapper;

import com.talan.polaris.dto.CareerPositionDTO;
import com.talan.polaris.entities.CareerPositionEntity;
import com.talan.polaris.entities.CollaboratorEntity;
import com.talan.polaris.entities.ProfileEntity;
import org.modelmapper.ModelMapper;

public class CareerPositionMapper {

     CareerPositionMapper() {
    }

    public static CareerPositionDTO convertCareerPostionToDTO(CareerPositionEntity careerPositionEntity, ModelMapper mapper) {
        return mapper.map(careerPositionEntity, CareerPositionDTO.class);
    }

    public static CareerPositionEntity convertCareerPositionToEntity(CareerPositionDTO careerPositionDTO, ModelMapper mapper) {
        CareerPositionEntity careerPositionEntity = new CareerPositionEntity();

        /*for(ProfileTypeDTO profile: careerPositionDTO.getSupervisor().getProfileTypeEntity()){
            if (profile.equals(ProfileTypeEnum.COLLABORATOR) || profile.equals(ProfileTypeEnum.MANAGER) ) {
                careerPositionEntity.setSupervisor(mapper.map(careerPositionDTO.getSupervisor(), CollaboratorEntity.class));
            }
        }*/
        careerPositionEntity.setProfile(mapper.map(careerPositionDTO.getProfile(), ProfileEntity.class));
        careerPositionEntity.setUpdatedAt(careerPositionDTO.getUpdatedAt());
        careerPositionEntity.setId(careerPositionDTO.getId());
        careerPositionEntity.setStartedAt(careerPositionDTO.getStartedAt());
        careerPositionEntity.setCollaborator(mapper.map(careerPositionDTO.getCollaborator(), CollaboratorEntity.class));
        careerPositionEntity.setCreatedAt(careerPositionDTO.getCreatedAt());
        careerPositionEntity.setStatus(careerPositionDTO.getStatus());
        careerPositionEntity.setSupervisor(mapper.map(careerPositionDTO.getSupervisor(), CollaboratorEntity.class));
        return careerPositionEntity;
    }


}
