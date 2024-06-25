package com.talan.polaris.dto;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoodCollabDTO {

	 
	  private Long idMood;
	  
	  private Date date ; 

	  private Long idCollab;

}
