package com.talan.polaris.services.impl;

import com.talan.polaris.entities.ParametrageAppliEntity;
import com.talan.polaris.enumerations.ParametrageAppliEnum;
import com.talan.polaris.repositories.ParametrageAppliRepository;
import com.talan.polaris.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * An implementation of {@link ParametrageAppliService}, containing business methods
 *
 * @author Imen Mechergui
 * @since 1.1.0
 */
@Service
public class ParametrageAppliServiceImpl implements ParametrageAppliService {
    public final ParametrageAppliRepository parametrageAppliRepository;


    private static final Logger LOGGER = LoggerFactory.getLogger(ParametrageAppliServiceImpl.class);

    @Autowired
    public ParametrageAppliServiceImpl(ParametrageAppliRepository parametrageAppliRepository) {
        this.parametrageAppliRepository = parametrageAppliRepository;

    }

    @Override
    public ParametrageAppliEntity findByParametre(ParametrageAppliEnum param) {
        LOGGER.info("findIdByParametre");
        return parametrageAppliRepository.findByParametre(ParametrageAppliEnum.DUREE_AUTO_VALIDATION_DEMANDE_ADMIN.getParam());
    }
}
