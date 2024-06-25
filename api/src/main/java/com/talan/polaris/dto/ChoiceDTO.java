package com.talan.polaris.dto;

import com.talan.polaris.enumerations.ChoiceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChoiceDTO extends GenericDTO{
    private ChoiceTypeEnum choiceType;
    private int position;
    private String content;
    private boolean enabled;
    private boolean valid;
    private boolean selected;
	@Override
	public String toString() {

		return new StringBuilder()
				.append("\n").append("ChoiceEntity").append("\n")
				.append(super.toString()).append("\n")
				.append("choiceType        = ").append(this.getChoiceType()).append("\n")
				.append("position          = ").append(this.getPosition()).append("\n")
				.append("content           = ").append(this.getContent()).append("\n")
				.append("enabled           = ").append(this.isEnabled())
				.toString();

	}
}
