/*
package com.talan.polaris.dto;
import com.talan.polaris.enumerations.AccountStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO{

	private Long id;
	private Instant createdAt;
	private Instant updatedAt;
	private String email;
    private AccountStatusEnum accountStatus;
	private String firstName;
	private String lastName;
	private String profileType;
	private LocalDate recruitedAt;
	private String secretWord;
	private boolean passedOnboardingProcess;
	private CollaboratorDTO collab;
	private Collection<ProfileTypeDTO> profileTypeEntity;
	@Override
	public String toString() {

		return new StringBuilder()
				.append("\n").append("UserDTO").append("\n")
				.append("id          = ").append(this.getId()).append("\n")
				.append("createdAt          = ").append(this.getCreatedAt()).append("\n")
				.append("updatedAt          = ").append(this.getUpdatedAt()).append("\n")
				.append("email             = ").append(this.getEmail()).append("\n")
				.append("password          = [ SECRET ]").append("\n")
				.append("accountStatus     = ").append(this.getAccountStatus()).append("\n")
				.append("firstName         = ").append(this.getFirstName()).append("\n")
				.append("profileType         = ").append(this.getProfileType()).append("\n")
				.append("lastName          = ").append(this.getLastName()).append("\n")
				.append("recruitedAt          = ").append(this.getRecruitedAt()).append("\n")
				.append("secretWord          = ").append(this.getSecretWord()).append("\n")
				.append("profileTypeEntity          = ").append(this.getProfileTypeEntity()).append("\n")
				.append("passedOnboardingProcess          = ").append(this.isPassedOnboardingProcess())
				.toString();

	}

}
*/
