package com.talan.polaris.entities;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Entity
public class TestQuestionEntity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EmbeddedId
    private TestQuestionKeysEntity id;
    private Long result;
    @ManyToOne
    @JoinColumn
    private TestEntity test;
    @ManyToOne
    @JoinColumn
    private QuestionTestEntity question;

}
