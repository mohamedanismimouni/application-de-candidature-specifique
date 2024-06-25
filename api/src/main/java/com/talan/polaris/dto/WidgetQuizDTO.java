
package com.talan.polaris.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
 
 


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WidgetQuizDTO{

	private Collection<QuizDTO> quiz;
	private Integer score;
 
	@Override
	public String toString() {

		return new StringBuilder()
				.append("\n").append("WidgetQuizDTO").append("\n")
				.append("score          = ").append(this.getScore()).append("\n")
				.append("quiz          = ").append(this.getQuiz()).append("\n")
				    
				.toString();

	}

}
