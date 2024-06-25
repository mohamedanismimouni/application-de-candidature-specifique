package com.talan.polaris.dto;

import lombok.Data;

@Data
public class QuestionConditionDTO {

    private PrmDifficultyDTO prmDifficultyDto;
   private PrmTechnologyDTO prmTechnologyDto;
    private Long nbQuestions;
    
}

