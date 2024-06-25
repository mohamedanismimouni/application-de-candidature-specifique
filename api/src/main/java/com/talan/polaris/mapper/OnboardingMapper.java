package com.talan.polaris.mapper;

import com.talan.polaris.dto.OnboardingDTO;
import com.talan.polaris.entities.OnboardingEntity;
import org.modelmapper.ModelMapper;

public class OnboardingMapper {

    private OnboardingMapper() {
    }

    public static OnboardingEntity convertOnboardingToEntity(OnboardingDTO onboardingDTO, ModelMapper modelMapper) {
        return modelMapper.map(onboardingDTO, OnboardingEntity.class);
    }


    public static OnboardingDTO convertOnboardingToDTO(OnboardingEntity onboardingEntity, ModelMapper modelMapper) {
        return modelMapper.map(onboardingEntity, OnboardingDTO.class);

    }
}
