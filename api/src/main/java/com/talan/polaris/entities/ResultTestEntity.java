package com.talan.polaris.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class ResultTestEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "test_id")
	private TestEntity test;
	@ManyToOne
	@JoinColumn
	private ResponseTestEntity response;
	@ManyToOne
	@JoinColumn
	private QuestionTestEntity question;

}
