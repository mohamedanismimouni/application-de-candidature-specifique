package com.talan.polaris.services.impl;

import com.talan.polaris.entities.ProfileTypeEntity;
import com.talan.polaris.enumerations.ProfileTypeEnum;
import com.talan.polaris.repositories.ProfileTypeRepository;
import com.talan.polaris.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileTypeServiceImpl implements ProfileTypeService {
    private  final ProfileTypeRepository profileTypeRepository;

    @Autowired
    public ProfileTypeServiceImpl(
            ProfileTypeRepository repository)
    {
        this.profileTypeRepository = repository;
    }

    @Override
    public ProfileTypeEntity findProfileTypeByLabel(ProfileTypeEnum label) {
        return this.profileTypeRepository.findProfileTypeByLabel(ProfileTypeEnum.COLLABORATOR).get();
    }
}
