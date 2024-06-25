package com.talan.polaris.dto;

import com.talan.polaris.enumerations.QuestionTypeEnum;
import com.talan.polaris.enumerations.SurveyTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO extends GenericDTO {
    private QuestionTypeEnum questionType;
    private SurveyTypeEnum surveyType;
    private int position;
    private String content;
    private boolean enabled;
    private boolean required;
    private int score;
    private boolean multipleChoices;
    private int lowestValue;
    private int highestValue;
    private Response response;
    private Collection<ChoiceDTO> choices;
	@Override
	public String toString() {

		return new StringBuilder()
				.append("\n").append("QuestionEntity").append("\n")
				.append(super.toString()).append("\n")
				.append("questionType      = ").append(this.getQuestionType()).append("\n")
				.append("surveyType        = ").append(this.getSurveyType()).append("\n")
				.append("position          = ").append(this.getPosition()).append("\n")
				.append("content           = ").append(this.getContent()).append("\n")
				.append("enabled           = ").append(this.isEnabled()).append("\n")
				.append("required          = ").append(this.isRequired())
				.toString();

	}
}
