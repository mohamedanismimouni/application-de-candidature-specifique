package com.talan.polaris.controllers;

import com.talan.polaris.dto.FunctionDTO;
import com.talan.polaris.mapper.FunctionMapper;
import com.talan.polaris.services.FunctionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;

@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/functions")
public class FunctionController {
    private final FunctionService functionService;
    private final ModelMapper modelMapper;
    @Autowired
    public FunctionController(FunctionService functionService, ModelMapper modelMapper) {
        this.functionService = functionService;
        this.modelMapper=modelMapper;
    }
    @GetMapping()
    public Collection<FunctionDTO> getFunctions() {
        return FunctionMapper.convertFunctionEntityListToDTO(this.functionService.findAll(), modelMapper);
    }
}
