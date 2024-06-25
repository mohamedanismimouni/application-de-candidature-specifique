package com.talan.polaris.services;

import java.util.Collection;

import com.talan.polaris.entities.EventEntity;

public interface EventService {
	
	public EventEntity addEvent(EventEntity event);
	public Collection<EventEntity> getUpcomingEvents();
	public Collection<EventEntity> getPastEvents();
	public EventEntity getEventById(Long id);
	public void archivePastEvent();
	public Collection<EventEntity> findActivePastEvents();
	public EventEntity participateEvent(Long idEvent,Long idCollab);
	public EventEntity cancelEvent(Long idEvent,Long idCollab);
	public void deleteEvent(Long idEvent, String deleteMotif);
	public EventEntity updateEvent(EventEntity event, Long id);


}
