package com.talan.polaris.mapper;

import com.talan.polaris.dto.DepartmentDTO;
import com.talan.polaris.entities.DepartmentEntity;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor
public class DepartmentMapper {
    public static DepartmentEntity convertDepartmentDTOToEntity (DepartmentDTO departmentDTO , ModelMapper modelMapper){

        return modelMapper.map(departmentDTO , DepartmentEntity.class);
    }

    public static  DepartmentDTO convertDepartmentEntityToDTO(DepartmentEntity departmentEntity , ModelMapper modelMapper){
        return modelMapper.map(departmentEntity, DepartmentDTO.class);
    }
}
