package com.talan.polaris.dto;

import com.talan.polaris.enumerations.MentorshipStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MentorshipDTO extends GenericDTO {
	private String menteeFeedback;
    private Integer menteeRating;
    private String mentorFeedback;
    private Integer mentorRating;
    private CareerPositionDTO careerPosition;
    private SkillDTO skill;
    private CollaboratorDTO mentor;
	private MentorshipStatusEnum status;

	@Override
	public String toString() {

		return new StringBuilder()
				.append("\n").append("MentorshipEntity").append("\n")
				.append(super.toString()).append("\n")
				.append("mentor            = ").append(this.getMentor()).append("\n")
				.append("skill             = ").append(this.getSkill()).append("\n")
				.append("careerPosition    = ").append(this.getCareerPosition()).append("\n")
				.append("status            = ").append(this.getStatus()).append("\n")
				.append("mentorRating      = ").append(this.getMentorRating()).append("\n")
				.append("mentorFeedback    = ").append(this.getMentorFeedback()).append("\n")
				.append("menteeRating      = ").append(this.getMenteeRating()).append("\n")
				.append("menteeFeedback    = ").append(this.getMenteeFeedback())
				.toString();

	}
}
