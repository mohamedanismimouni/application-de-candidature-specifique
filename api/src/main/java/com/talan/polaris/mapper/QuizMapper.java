package com.talan.polaris.mapper;
import com.talan.polaris.dto.QuizDTO;
import com.talan.polaris.entities.QuizEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

 
public class QuizMapper {

    private QuizMapper() {
    }

  
    public static QuizDTO convertQuizEntityToDTO(Collection<QuizEntity> collection, ModelMapper modelMapper) {
        return modelMapper.map(collection, QuizDTO.class);

    }

   
    public static Collection<QuizDTO> convertQuizEntityListToDTO(Collection<QuizEntity> quizEntityList, ModelMapper modelMapper) {
        Type quizListType = new TypeToken<List<QuizDTO>>() {
        }.getType();
        return modelMapper.map(quizEntityList, quizListType);

    }
}
