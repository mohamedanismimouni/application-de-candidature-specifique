package com.talan.polaris.controllers;

import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talan.polaris.dto.keycloakmodelsdto.KeycloakUserDTO;
import com.talan.polaris.enumerations.ProfileTypeEnum;
import com.talan.polaris.services.KeycloakService;

@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/keycloak")
public class KeycloakController {

	@Autowired
	private KeycloakService keycloakService;

	@GetMapping(path = "/users-by-role", params = { "role" })
	public Collection<KeycloakUserDTO> getUsersByClientRole(@RequestParam(value = "role", required = true) ProfileTypeEnum role) 
	{
		
		return keycloakService.findUsersByClientRole(role);
		
	}

}
