package com.talan.polaris.controllers;

import static com.talan.polaris.constants.CommonConstants.EVENT_COMPONENT_LABEL;
import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;

import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.talan.polaris.dto.EventDTO;
import com.talan.polaris.mapper.EventMapper;
import com.talan.polaris.services.EventService;
import com.talan.polaris.services.ScoreService;

@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/event")
public class EventController {
	private final ScoreService scoreService;
	private final EventService eventService;
	private final ModelMapper modelMapper;
	
	 @Autowired
	    public EventController(EventService eventService, ModelMapper modelMapper, ScoreService scoreService) {
	        this.scoreService = scoreService;
			this.eventService = eventService;
	        this.modelMapper = modelMapper;
	    }
	 
	 @GetMapping(path = "/upcoming")
	    public Collection<EventDTO> getUpcomingEvents() {
	        return EventMapper.convertEventListToDTO(this.eventService.getUpcomingEvents(), modelMapper);
	    }
	
	 @GetMapping(path = "/past")
	    public Collection<EventDTO> getPastEvents() {
	        return EventMapper.convertEventListToDTO(this.eventService.getPastEvents(), modelMapper);
	    }
	 
	 @PostMapping()
	 	public EventDTO addEvent(@RequestBody EventDTO event )  {
		 return EventMapper.convertEventEntityToDTO(this.eventService.addEvent(EventMapper.convertEventDTOToEntity(event, modelMapper)), modelMapper);
	 }
	  @GetMapping()
	    public Integer getEventScore() {
		   return scoreService.getScore(EVENT_COMPONENT_LABEL);
	   }


	@GetMapping(path = "/{eventId}")
	public EventDTO getEventById(@PathVariable(value = "eventId", required = true) Long eventId) {
		return EventMapper.convertEventEntityToDTO(this.eventService.getEventById(eventId), modelMapper);
	}

	@PostMapping(path = "/deleteEvent/{eventId}")
	public void deleteEvent(@PathVariable(value = "eventId", required = true) Long eventId, @RequestBody(required = false)  String deleteMotif) {
		eventService.deleteEvent(eventId, deleteMotif);
	}

	@PostMapping(path = "/update-event/{eventId}")
	public void updateEvent(@PathVariable(value = "eventId", required = true) Long eventId, @RequestBody EventDTO eventDTO)
		{
			this.eventService.updateEvent(EventMapper.convertEventDTOToEntity(eventDTO, modelMapper), eventId);
		}

	@GetMapping(path = "participate/{eventId}/{collabId}")
	public EventDTO participateToEvent(@PathVariable(value = "eventId", required = true) Long eventId,@PathVariable(value = "collabId", required = true) Long collabId) {
		return  EventMapper.convertEventEntityToDTO(this.eventService.participateEvent(eventId,collabId), modelMapper);
	}
	@GetMapping(path = "cancel/{eventId}/{collabId}")
	public EventDTO cancelEvent(@PathVariable(value = "eventId", required = true) Long eventId,@PathVariable(value = "collabId", required = true) Long collabId) {
		return  EventMapper.convertEventEntityToDTO(this.eventService.cancelEvent(eventId,collabId), modelMapper);
	}



}
