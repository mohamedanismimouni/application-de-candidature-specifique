package com.talan.polaris.mapper;

import com.talan.polaris.dto.ChoiceDTO;
import com.talan.polaris.dto.QuestionDTO;
import com.talan.polaris.entities.*;
import com.talan.polaris.enumerations.QuestionTypeEnum;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * Mapping Question Entity
 *
 * @author Imen mechergui
 * @since 1.0.0
 */
public class QuestionMapper {
    private QuestionMapper() {

    }

    /**
     * convert Question DTO To Entity
     *
     * @param questionDTO
     * @param modelMapper
     * @return QuestionEntity
     */

    public static QuestionEntity convertQuestionDTOToEntity(QuestionDTO questionDTO, ModelMapper modelMapper) {
        if (questionDTO.getQuestionType().equals(QuestionTypeEnum.OPEN_ENDED)) {

            return modelMapper.map(questionDTO, OpenEndedQuestionEntity.class);

        } else if (questionDTO.getQuestionType().equals(QuestionTypeEnum.REGULAR_CHOICE)) {

            return modelMapper.map(questionDTO, RegularChoiceQuestionEntity.class);

        } else if (questionDTO.getQuestionType().equals(QuestionTypeEnum.RATING_SCALE)) {

            return modelMapper.map(questionDTO, RatingScaleQuestionEntity.class);


        } else {
            return modelMapper.map(questionDTO, QuizChoiceQuestionEntity.class);

        }
    }

    /**
     * convert Question And Choices DTO To Entity
     *
     * @param questionDTO
     * @param modelMapper
     * @return QuestionEntity
     */
    public static QuestionEntity convertQuestionAndChoicesDTOToEntity(QuestionDTO questionDTO, ModelMapper modelMapper) {
        List<ChoiceEntity> choicesList = new ArrayList<>();
        QuizChoiceQuestionEntity quizQuestion;
        RegularChoiceQuestionEntity regularQuestion;
        //OPEN_ENDED question
        if (questionDTO.getQuestionType().equals(QuestionTypeEnum.OPEN_ENDED)) {
            return modelMapper.map(questionDTO, OpenEndedQuestionEntity.class);

        }
        //Regular choice question
        else if (questionDTO.getQuestionType().equals(QuestionTypeEnum.REGULAR_CHOICE)) {
            List<ChoiceDTO> choices = new ArrayList<>(questionDTO.getChoices());
            for (int i = 0; i < choices.size(); i++) {
                choicesList.add(ChoiceMapper.convertChoiceDTOToEntity(choices.get(i), modelMapper));
            }
            regularQuestion = modelMapper.map(questionDTO, RegularChoiceQuestionEntity.class);
            regularQuestion.setChoices(choicesList);
            return regularQuestion;
        }
        //rating scale Question
        else if (questionDTO.getQuestionType().equals(QuestionTypeEnum.RATING_SCALE)) {
            return modelMapper.map(questionDTO, RatingScaleQuestionEntity.class);

        }
        //quiz question
        else {
            List<ChoiceDTO> choices = new ArrayList<>(questionDTO.getChoices());
            for (int i = 0; i < choices.size(); i++) {
                choicesList.add(ChoiceMapper.convertChoiceDTOToEntity(choices.get(i), modelMapper));
            }
            quizQuestion = modelMapper.map(questionDTO, QuizChoiceQuestionEntity.class);
            quizQuestion.setChoices(choicesList);
            return quizQuestion;

        }


    }

    /**
     * convert Question Entity To DTO
     *
     * @param questionEntity
     * @param modelMapper
     * @return QuestionDTO
     */

    public static QuestionDTO convertQuestionEntityToDTO(QuestionEntity questionEntity, ModelMapper modelMapper) {
        return modelMapper.map(questionEntity, QuestionDTO.class);
    }

    /**
     * convert Question Entity List To DTO
     *
     * @param questionEntityList
     * @param modelMapper
     * @return Collection<QuestionDTO>
     */

    public static Collection<QuestionDTO> convertQuestionEntityListToDTO(Collection<QuestionEntity> questionEntityList, ModelMapper modelMapper) {
        Type questionListType = new TypeToken<List<QuestionDTO>>() {
        }.getType();
        return modelMapper.map(questionEntityList, questionListType);

    }
}
