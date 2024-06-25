package com.talan.polaris.services;
 
import com.talan.polaris.dto.WidgetQuizDTO;
  
public interface QuizService {

    public WidgetQuizDTO findUnansweredQuiz(String email);
    public WidgetQuizDTO addCollaboratorToQuiz(String email, String id,String response);
}
