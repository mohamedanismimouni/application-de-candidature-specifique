package com.talan.polaris.services.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static com.talan.polaris.constants.ConfigurationConstants.KEYCLOAK_GET_CLIENT_ID;
import static com.talan.polaris.constants.ConfigurationConstants.KEYCLOAK_GET_USERS_BY_CLIENT_ROLE;
import static com.talan.polaris.constants.ConfigurationConstants.KEYCLOAK_GET_ACCESS_TOKEN;
import static com.talan.polaris.constants.ConfigurationConstants.KEYCLOAK_USERNAME;
import static com.talan.polaris.constants.ConfigurationConstants.KEYCLOAK_PASSWORD;
import static com.talan.polaris.constants.ConfigurationConstants.KEYCLOAK_FRONT_CLIENT;
import static com.talan.polaris.constants.ConfigurationConstants.KEYCLOAK_BACK_CLIENT;

import com.talan.polaris.dto.keycloakmodelsdto.KeycloakClientDTO;
import com.talan.polaris.dto.keycloakmodelsdto.KeycloakTokenDTO;
import com.talan.polaris.dto.keycloakmodelsdto.KeycloakUserDTO;
import com.talan.polaris.enumerations.ProfileTypeEnum;
import com.talan.polaris.services.KeycloakService;

@Service
public class KeycloakServiceImpl implements KeycloakService {

	@Value("${" + KEYCLOAK_GET_CLIENT_ID + "}")
	private String keycloakGetClientIdPath;

	@Value("${" + KEYCLOAK_GET_USERS_BY_CLIENT_ROLE + "}")
	private String keycloakGetUsersByClientRolePath;
	
	@Value("${" + KEYCLOAK_GET_ACCESS_TOKEN + "}")
	private String keycloakGetAccessTokenPath;
	
	@Value("${" + KEYCLOAK_USERNAME + "}")
	private String username;
	
	@Value("${" + KEYCLOAK_PASSWORD + "}")
	private String password;
	
	@Value("${" + KEYCLOAK_FRONT_CLIENT + "}")
	private String frontClient;
	
	@Value("${" + KEYCLOAK_BACK_CLIENT + "}")
	private String backClient;

	@Autowired
	private RestTemplate restTemplate ;

	@Override
	public Collection<KeycloakUserDTO> findUsersByClientRole(ProfileTypeEnum role) {

		String accessTokenUrl = keycloakGetAccessTokenPath;
		String clientIdUrl = keycloakGetClientIdPath;
		String usersByRoleUrl = keycloakGetUsersByClientRolePath;
		
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
       
		body.add("grant_type", "password");
		body.add("username", username);
		body.add("password", password);
		body.add("client_id", frontClient);
		
		HttpHeaders loginHeaders = new HttpHeaders();
		loginHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		HttpEntity<MultiValueMap<String, String>> loginEntity = new HttpEntity<>(body,loginHeaders);
		
		HttpEntity<KeycloakTokenDTO> accessTokenResponse = restTemplate.exchange(accessTokenUrl,
				HttpMethod.POST, loginEntity, KeycloakTokenDTO.class );
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessTokenResponse.getBody().getAccess_token());

		UriComponents builder = UriComponentsBuilder.fromHttpUrl(clientIdUrl).queryParam("clientId", backClient)
				.build();
		
		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<List<KeycloakClientDTO>> clientIdResponse = restTemplate.exchange(builder.toUriString(),
				HttpMethod.GET, entity, new ParameterizedTypeReference<List<KeycloakClientDTO>>() {
				});

		String clientId = clientIdResponse.getBody().get(0).getId();

		HttpEntity<List<KeycloakUserDTO>> userResponse = restTemplate.exchange(usersByRoleUrl, HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<KeycloakUserDTO>>() {
				}, clientId, role);

		return userResponse.getBody();

	}

}
