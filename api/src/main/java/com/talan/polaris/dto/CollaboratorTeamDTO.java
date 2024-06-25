package com.talan.polaris.dto;
import com.talan.polaris.enumerations.AccountStatusEnum;
import com.talan.polaris.enumerations.ProfileTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CollaboratorTeamDTO {
	private Long id;
	private Instant createdAt;
	private Instant updatedAt;
    //private ProfileTypeEnum profileType;
	private String email;
    private AccountStatusEnum accountStatus;
	private String firstName;
	private String lastName;
	private LocalDate recruitedAt;
	private TeamDTO memberOf;
	private String secretWord;
	private boolean passedOnboardingProcess;
	private Integer score;
	@Override
	public String toString() {

		return new StringBuilder()
				.append("\n").append("UserDTO").append("\n")
				.append("id          = ").append(this.getId()).append("\n")
				.append("createdAt          = ").append(this.getCreatedAt()).append("\n")
				.append("updatedAt          = ").append(this.getUpdatedAt()).append("\n")
				/*.append("profileType       = ").append(this.getProfileType()).append("\n")*/
				.append("email             = ").append(this.getEmail()).append("\n")
				.append("password          = [ SECRET ]").append("\n")
				.append("accountStatus     = ").append(this.getAccountStatus()).append("\n")
				.append("firstName         = ").append(this.getFirstName()).append("\n")
				.append("lastName          = ").append(this.getLastName())
				.toString();

	}

}
