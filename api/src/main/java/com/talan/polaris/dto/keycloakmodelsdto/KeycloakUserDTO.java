package com.talan.polaris.dto.keycloakmodelsdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeycloakUserDTO {

	private String firstName;
	private String lastName;
	private String email;
}
