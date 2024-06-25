package com.talan.polaris.services.impl;

import com.talan.polaris.entities.CivilityEntity;
import com.talan.polaris.repositories.CivilityRepository;
import com.talan.polaris.services.CivilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CivilityServiceImpl implements CivilityService {
    public final CivilityRepository civilityRepository;

    @Autowired
    public CivilityServiceImpl(CivilityRepository civilityRepository) {
        this.civilityRepository=civilityRepository;

    }

    @Override
    public CivilityEntity findIdByLabel (String label)
    {
        return this.civilityRepository.findByAbrev(label);
    }
}
