package com.talan.polaris.mapper;

import com.talan.polaris.dto.FunctionDTO;
import com.talan.polaris.dto.TeamDTO;
import com.talan.polaris.entities.FunctionEntity;
import com.talan.polaris.entities.TeamEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class FunctionMapper {

    private FunctionMapper(){
    }
    public static Collection<FunctionDTO> convertFunctionEntityListToDTO(Collection<FunctionEntity> functionEntityList, ModelMapper modelMapper) {
        Type teamListType = new TypeToken<List<FunctionDTO>>() {}.getType();
        return modelMapper.map(functionEntityList, teamListType);

    }
}
