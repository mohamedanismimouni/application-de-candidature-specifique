package com.talan.polaris.services.impl;

import com.talan.polaris.entities.QuestionEntity;
import com.talan.polaris.enumerations.QuestionTypeEnum;
import com.talan.polaris.enumerations.SurveyTypeEnum;
import com.talan.polaris.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
/**
 * Unit tests class for methods implemented in {@link QuestionServiceImpl}.
 * 
 * @author Imen Mechergui
 * @since 1.0.0
 */
public class QuestionServiceImplUnitTests {

    @Mock
    private QuestionRepository questionRepository;
    @InjectMocks
    private QuestionServiceImpl questionServiceImpl;
    @BeforeEach
    public void setup() {
        initMocks(this);
    }


    @Test
    public void findQuizChoicesByValidStatus_givenQuestionId_whenCalled_thenCallQuestionRepository() {
      // given + when
        this.questionServiceImpl.findQuizChoicesByValidStatus(anyString(),anyBoolean());
        // then
        verify(this.questionRepository, only()).findQuizChoicesByValidStatus(anyString(),anyBoolean());
    }

    @Test
    public void deleteQuestion_givenQuestionID_whenQuestionIsFound_thenCallQuestionRepository() {

        // given
        String questionID=UUID.randomUUID().toString();
        QuestionServiceImpl questionServiceImplSpy = spy(this.questionServiceImpl);
        doReturn(new QuestionEntity())
                .when(questionServiceImplSpy)
                .findById(anyString());
        // when
        questionServiceImplSpy.deleteQuestion(questionID);

        // then
      verify(questionRepository, times(1)).delete(any(QuestionEntity.class));

    }
    @Test
    public void deleteChoice_givenQuestionIDAndChoiceID_whenQuestionIsFound_thenChoiceIsDeleted() {
        // given
        String questionID=UUID.randomUUID().toString();
        String choiceID=UUID.randomUUID().toString();
        QuestionServiceImpl questionServiceImplSpy = spy(this.questionServiceImpl);
        doAnswer((invocation) -> invocation.getArgument(0))
                .when(questionServiceImplSpy)
                .deleteChoice(anyString(),anyString());
        // when
        questionServiceImplSpy.deleteChoice( questionID,choiceID);
        // then
       verify(questionServiceImplSpy, times(1)).deleteChoice( questionID,choiceID);

    }


    @Test
    public void createQuestion_givenQuestionEntity_whenSavingQuestion_thenQuestionIsCreated() {
        //Profile Entity
        QuestionEntity question = new QuestionEntity();
       question.setEnabled(true);
       question.setQuestionType(QuestionTypeEnum.OPEN_ENDED);
       question.setContent("test");
       question.setPosition(1);
       question.setSurveyType(SurveyTypeEnum.AWARENESS);
       question.setId(UUID.randomUUID().toString());
       question.setCreatedAt(Instant.now());
       question.setUpdatedAt(Instant.now());
        //spy QuestionServiceImpl
        QuestionServiceImpl questionServiceImplSpy = spy(this.questionServiceImpl);
        //get result from request
        doAnswer((invocation) -> invocation.getArgument(0))
                .when(questionServiceImplSpy)
                .create(any(QuestionEntity.class));
        // when
       QuestionEntity createdQuestion= questionServiceImplSpy.create(question);
        // then
        assertThat(createdQuestion).isNotNull();
        assertThat(createdQuestion.getContent()).isEqualTo(question.getContent());
    }
}



