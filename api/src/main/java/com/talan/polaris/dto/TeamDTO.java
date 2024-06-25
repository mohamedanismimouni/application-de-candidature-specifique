package com.talan.polaris.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO extends GenericDTO{
	private String name;
	private String teamEvaluationDate;
	private CollaboratorDTO managedBy;
	@Override
	public String toString() {

		return new StringBuilder()
				.append("\n").append("TeamEntity").append("\n")
				.append(super.toString()).append("\n")
				.append("name              = ").append(this.getName()).append("\n")
				.append("evaluationDate    = ").append(this.getTeamEvaluationDate()).append("\n")
				.toString();

	}
}
