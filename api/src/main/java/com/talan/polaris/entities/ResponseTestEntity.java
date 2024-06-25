package com.talan.polaris.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@JsonIgnoreProperties(value = {"question"})
public class ResponseTestEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String value;
	private Boolean correct;
	@ManyToOne
	@JoinColumn
	@JsonProperty("question")
	private QuestionTestEntity question;
}
