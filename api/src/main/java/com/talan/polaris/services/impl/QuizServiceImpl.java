package com.talan.polaris.services.impl;


import com.talan.polaris.dto.QuizDTO;
import com.talan.polaris.dto.WidgetQuizDTO;
import com.talan.polaris.entities.CollaboratorEntity;
import com.talan.polaris.entities.QuizEntity;
import com.talan.polaris.mapper.QuizMapper;
import com.talan.polaris.services.ScoreService;
import java.util.Collection;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.talan.polaris.repositories.QuizRepository;
import com.talan.polaris.repositories.CollaboratorRepository;
import com.talan.polaris.services.QuizService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.talan.polaris.constants.CommonConstants.QUIZ_COMPONENT_LABEL;


@Service
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final CollaboratorRepository collaboratorRepository;
	private final ScoreService scoreService ;
    private final ModelMapper modelMapper;


    @Autowired
    public QuizServiceImpl(ScoreService scoreService,
            QuizRepository repository, ModelMapper modelMapper
          ,  CollaboratorRepository collaboratorRepository ) {
        this.collaboratorRepository = collaboratorRepository;
		this.modelMapper = modelMapper;
        this.quizRepository = repository;
    	this.scoreService = scoreService;


    }
   
    
   

    @Override
    public WidgetQuizDTO findUnansweredQuiz(String email) {
    	Collection<QuizDTO> quizList;   
    
    	WidgetQuizDTO widgetQuiz = new WidgetQuizDTO() ;
    	quizList = QuizMapper.convertQuizEntityListToDTO(this.quizRepository.findUnansweredQuiz(email), modelMapper);
    	Long x = (long) 3;
    	 
    	quizList= quizList.stream().map(element -> 
    				this.shuffle(element)).collect(Collectors.toList());
    
    	widgetQuiz.setQuiz(quizList);
    	widgetQuiz.setScore(scoreService.getScore(QUIZ_COMPONENT_LABEL));
    	return widgetQuiz;
    }
     
   
    private QuizDTO shuffle(QuizDTO word) {
        String shuffledWord = word.getResponse();  
        
        int wordSize = word.getResponse().length();
        int shuffleCount = 20;  
        for(int i=0;i<shuffleCount;i++) {
             int position1 = ThreadLocalRandom.current().nextInt(0, wordSize);
            int position2 = ThreadLocalRandom.current().nextInt(0, wordSize);
            shuffledWord = swapCharacters(shuffledWord,position1,position2);
        }
        String [] alphabets =  shuffledWord.split("");
        Boolean [] disabled = new Boolean[shuffledWord.length()];
        for (int i=0; i<shuffledWord.length();i++) {
        	 
        	disabled[i]=false;
        	
        }
         word.setAlphabets(alphabets);
        word.setDisabled(disabled);
        word.setResponse("");
        return word;
    }
     
   
    private String swapCharacters(String shuffledWord, int position1, int position2) {
        char[] charArray = shuffledWord.toCharArray();
        char temp = charArray[position1];
        charArray[position1] = charArray[position2];
        charArray[position2] = temp;
        return new String(charArray);
    }
	@Override
	public WidgetQuizDTO addCollaboratorToQuiz(String email, String id,String response) {
		// TODO Auto-generated method stub
		
		QuizEntity quiz = this.quizRepository.findQuizById(id);
		if (quiz != null) {
			if (response.equals(quiz.getResponse())) {
				CollaboratorEntity collab = this.collaboratorRepository.findByEmail(email) ;
		        if (!quiz.getAnsweredBy().stream().anyMatch(x -> x == collab)){
		        	quiz.addAnsweredBy(collab);
		        	this.quizRepository.save(quiz);
		        	//update score
		         	scoreService.increment(collab.getId(),QUIZ_COMPONENT_LABEL );
		        }
		    	
		    	return  findUnansweredQuiz(email);	
			}	
		}
		
		 
    	return null;
	}

}
