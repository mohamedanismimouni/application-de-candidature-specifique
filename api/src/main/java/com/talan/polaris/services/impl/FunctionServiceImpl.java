package com.talan.polaris.services.impl;

import com.talan.polaris.entities.FunctionEntity;
import com.talan.polaris.repositories.FunctionRepository;
import com.talan.polaris.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FunctionServiceImpl implements FunctionService {

    private final FunctionRepository functionRepository;
    @Autowired
    public FunctionServiceImpl(FunctionRepository repository) {
        this.functionRepository = repository;
    }

    @Override
    public Collection<FunctionEntity> findAll() {
        return this.functionRepository.findAll();
    }

    @Override
    public FunctionEntity findFunctionByLibelle(String libelle) {
        return this.functionRepository.findFunctionByLibelle(libelle);
    }
}
