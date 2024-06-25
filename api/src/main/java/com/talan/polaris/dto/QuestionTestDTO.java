package com.talan.polaris.dto;

import lombok.Data;


@Data
public class QuestionTestDTO {

	private Long questionid;
	private String statement;
	private String code;

    private PrmDifficultyDTO prmDifficulty;
    private PrmTechnologyDTO prmTechnology;









}
