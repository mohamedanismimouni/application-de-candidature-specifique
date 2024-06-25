package com.talan.polaris.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable

public class TestQuestionKeysEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column
    Long testid;
    @Column
    Long questionid  ;
}
