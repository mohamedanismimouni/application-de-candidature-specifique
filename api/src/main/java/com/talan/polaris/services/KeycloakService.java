package com.talan.polaris.services;

import java.util.Collection;

import com.talan.polaris.dto.keycloakmodelsdto.KeycloakUserDTO;
import com.talan.polaris.enumerations.ProfileTypeEnum;

public interface KeycloakService {

	public Collection<KeycloakUserDTO> findUsersByClientRole(ProfileTypeEnum role);
}
