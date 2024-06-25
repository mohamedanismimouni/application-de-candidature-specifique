package com.talan.polaris.services;

import com.talan.polaris.entities.FunctionEntity;

import java.util.Collection;

public interface FunctionService {
    public Collection<FunctionEntity> findAll();
    public FunctionEntity findFunctionByLibelle(String libelle);
}
