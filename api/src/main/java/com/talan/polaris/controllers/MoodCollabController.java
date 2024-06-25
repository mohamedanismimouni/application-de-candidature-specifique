package com.talan.polaris.controllers;

import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.talan.polaris.dto.DocumentRequestDTO;
import com.talan.polaris.mapper.DocumentRequestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.talan.polaris.dto.MoodCollabDTO;
import com.talan.polaris.entities.MoodCollab;
import com.talan.polaris.services.MoodCollabService;
import com.talan.polaris.services.ScoreService;

@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/mood-paths")
public class MoodCollabController {
	private final ScoreService scoreService ;
	private final MoodCollabService moodCollabService;
	 @Autowired
	    public MoodCollabController(MoodCollabService moodCollabService, ScoreService scoreService 
) {
	        this.scoreService = scoreService;
			this.moodCollabService = moodCollabService;
	    }
	   @PostMapping()
	   public MoodCollab addHumeur(@RequestBody @Valid MoodCollabDTO moodCollabDTO) {
	        return moodCollabService.createMood(moodCollabDTO) ;
	    }
	   
	   @GetMapping(params = {"id"},path = "/actual-mood")
	    public Boolean findMood(
	            @RequestParam(value = "id", required = true) @NotNull Long id) {
		  
		   Date today = new Date();
		  
		   List<MoodCollab>moodList= moodCollabService.findByDate(id, today);
		   if (moodList.size()!=0)
			   
			   return( true);
		   else 
			   return (false);

	    }
	   @PutMapping(path = "/update-mood")
	   public MoodCollab updateMood(@RequestParam(value = "id", required = true) @NotNull Long id,
			   @RequestBody @Valid MoodCollabDTO moodCollabDTO) 
	   {
		   Date today = new Date();
		   
		   return moodCollabService.updateMood(moodCollabDTO,id,today);
	   }
	   @GetMapping()
	    public Integer getMoodScore() {
		   return scoreService.getScore("How do you feel today");
	   }


	   @GetMapping(path = "/current-mood")
	   public MoodCollab getActualMood(
			   @RequestParam(value = "collaboratorId", required = true) Long collaboratorId){
		   Date today = new Date();
		return moodCollabService.findActualMood(collaboratorId);
	      }


}
