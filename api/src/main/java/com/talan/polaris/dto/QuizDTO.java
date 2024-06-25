package com.talan.polaris.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
 
 


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizDTO{

	private Long id;
	private Instant createdAt;
	private Instant updatedAt;
 	private String question;
 	private String response;
 	private String [] alphabets;
 	private Boolean [] disabled;
 
	@Override
	public String toString() {

		return new StringBuilder()
				.append("\n").append("UserDTO").append("\n")
				.append("id          = ").append(this.getId()).append("\n")
				.append("createdAt          = ").append(this.getCreatedAt()).append("\n")
				.append("updatedAt          = ").append(this.getUpdatedAt()).append("\n")
				.append("question       = ").append(this.getQuestion()).append("\n")
				.append("response             = ").append(this.getResponse()).append("\n")	  
				.toString();

	}

}
