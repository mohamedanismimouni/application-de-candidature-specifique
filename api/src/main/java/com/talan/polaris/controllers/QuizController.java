package com.talan.polaris.controllers;


import com.talan.polaris.dto.WidgetQuizDTO;
import com.talan.polaris.services.QuizService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;
import javax.servlet.http.HttpServletRequest;
 
 
@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/quiz")
@Validated
public class QuizController {

     private final QuizService quizService;
 
     @Autowired
	 private HttpServletRequest request;
    @Autowired
    public QuizController( QuizService quizService ) {
        this.quizService = quizService;
     }

    

    @GetMapping()
    public WidgetQuizDTO getQuizs() {
    	KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();        
        KeycloakPrincipal principal=(KeycloakPrincipal)token.getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        AccessToken accessToken = session.getToken();
    	return this.quizService.findUnansweredQuiz(accessToken.getEmail());
         
    }
   
    @GetMapping(params = { "id","response"})
    public WidgetQuizDTO addCollabToQuiz(@RequestParam(value = "id", required = true) String id,@RequestParam(value = "response", required = true) String response) {
    	KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();        
        KeycloakPrincipal principal=(KeycloakPrincipal)token.getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        AccessToken accessToken = session.getToken();
    	return this.quizService.addCollaboratorToQuiz(accessToken.getEmail(), id,response);

    }
}
