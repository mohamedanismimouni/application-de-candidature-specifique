package com.talan.polaris.services;


import com.talan.polaris.entities.ParametrageAppliEntity;
import com.talan.polaris.enumerations.ParametrageAppliEnum;

/**
 *
 * definitions related to {@linkParametrageAppliEntity}.
 *
 * @author Imen Mechergui
 * @since 1.1.0
 */
public interface ParametrageAppliService {
     ParametrageAppliEntity findByParametre (ParametrageAppliEnum param);

}
