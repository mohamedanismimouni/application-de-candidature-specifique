package com.talan.polaris.services.impl;

import com.talan.polaris.entities.EntretienEntity;
import com.talan.polaris.repositories.EntretienRepository;
import com.talan.polaris.services.EntretienService;
import org.springframework.beans.factory.annotation.Autowired;

public class EntretienServiceImpl implements EntretienService {

    @Autowired
    private EntretienRepository entretienRepository;

    @Override
    public EntretienEntity addEntretien(EntretienEntity entretien) {
        return entretienRepository.save(entretien);
    }
}
