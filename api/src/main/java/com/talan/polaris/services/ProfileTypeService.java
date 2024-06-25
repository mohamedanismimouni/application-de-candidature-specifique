package com.talan.polaris.services;

import com.talan.polaris.entities.ProfileTypeEntity;
import com.talan.polaris.enumerations.ProfileTypeEnum;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileTypeService {
    public ProfileTypeEntity findProfileTypeByLabel(ProfileTypeEnum label);
}
