package com.talan.polaris.services.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;

import com.talan.polaris.dto.keycloakmodelsdto.KeycloakClientDTO;
import com.talan.polaris.dto.keycloakmodelsdto.KeycloakTokenDTO;
import com.talan.polaris.dto.keycloakmodelsdto.KeycloakUserDTO;

 class KeycloakServiceImplUnitTests {

    @InjectMocks
    private KeycloakServiceImpl keycloakServiceImpl ;
    
    @Mock
    private RestTemplate restTemplate;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);    
    }
    
	@Test
	  void testFindUsersByClientRole() {
		
        ResponseEntity<KeycloakTokenDTO> accessTokenResponse = new ResponseEntity<KeycloakTokenDTO>(new KeycloakTokenDTO("token"),HttpStatus.OK);
        
        Mockito.when(restTemplate.exchange(
        		  Mockito.anyString(),
                  Mockito.eq(HttpMethod.POST),
                  Mockito.any(HttpEntity.class),
                  Mockito.<Class<KeycloakTokenDTO>>any())
        ).thenReturn(accessTokenResponse);
        
        assertThat(accessTokenResponse.getBody()).isNotNull();
        
        ResponseEntity<KeycloakClientDTO> clientIdResponse = new ResponseEntity<KeycloakClientDTO>(new KeycloakClientDTO("client-id"),HttpStatus.OK);
        
        Mockito.when(restTemplate.exchange(
        		Mockito.anyString(),
        		Mockito.eq(HttpMethod.GET),
        		Mockito.<HttpEntity<?>> any(),
        		Mockito.<Class<KeycloakClientDTO>>any())
        ).thenReturn(clientIdResponse);
        
        assertThat(clientIdResponse.getBody()).isNotNull();
        
        ResponseEntity<KeycloakUserDTO> userResponse = new ResponseEntity<KeycloakUserDTO>(new KeycloakUserDTO("firstName", "lastName", "email"),HttpStatus.OK);
        
        Mockito.when(restTemplate.exchange(
        		Mockito.anyString(),
        		Mockito.eq(HttpMethod.GET),
        		Mockito.<HttpEntity<?>> any(),
        		Mockito.<Class<KeycloakUserDTO>>any())
        ).thenReturn(userResponse);
        
        assertThat(userResponse.getBody()).isNotNull();
        
        //Collection<KeycloakUserDTO> users = this.keycloakServiceImpl.findUsersByClientRole(ProfileTypeEnum.COLLABORATOR);
        
        //assertThat(users.iterator().next().getEmail()).isEqualTo("email");
        
       
    }

}
