package com.talan.polaris.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Data
@Entity
@JsonIgnoreProperties(value = {"test"})
public class QuestionTestEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long questionid;
	private String statement;
	@Column
	private String code;

	@ManyToOne
	@JoinColumn
	private PrmDifficultyEntity prmDifficulty;

	@ManyToOne
	@JoinColumn
	private PrmTechnologyEntity prmTechnology;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
	private Collection<ResponseTestEntity> responses ;

	@JsonProperty("test")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "TEST_QUESTIONS",
			joinColumns = @JoinColumn(name = "QUESTION_ID",
			referencedColumnName = "questionid"),
			inverseJoinColumns = @JoinColumn(name = "TEST_ID",
					referencedColumnName = "testid"))
	private Collection<TestEntity> tests;

}
