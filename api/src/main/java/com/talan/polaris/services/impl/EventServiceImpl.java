package com.talan.polaris.services.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import com.talan.polaris.constants.CommonConstants;
import com.talan.polaris.entities.CollaboratorEntity;
import com.talan.polaris.entities.JobHistoryEntity;
import com.talan.polaris.enumerations.EventStatusEnum;
import com.talan.polaris.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.talan.polaris.entities.EventEntity;
import com.talan.polaris.repositories.EventRepository;
import org.springframework.transaction.annotation.Transactional;


import static com.talan.polaris.constants.CommonConstants.*;
import static com.talan.polaris.constants.ConfigurationConstants.ARCHIVE_PAST_EVENT_CRON;

@Service
public class EventServiceImpl implements EventService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);

    private final EventRepository eventRepository;
    private final ScoreService scoreService;
    private final JobHistoryService jobHistoryService;
    private final CollaboratorService collaboratorService;
    private final EDMService edmService;
    private final MailService mailService;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository,
                            ScoreService scoreService,
                            JobHistoryService jobHistoryService,
                            EDMService edmService,
                            CollaboratorService collaboratorService,
                            MailService mailService) {

        this.eventRepository = eventRepository;
        this.scoreService = scoreService;
        this.jobHistoryService = jobHistoryService;
        this.edmService = edmService;
        this.collaboratorService = collaboratorService;
        this.mailService = mailService;
    }

    /**
     * participate to event
     *
     * @param idEvent
     * @param idCollab
     * @return
     */
    @Override
    @Transactional
    public EventEntity participateEvent(Long idEvent, Long idCollab) {
        EventEntity foundEvent = null;
        try {
            LOGGER.info("start participate to event");
            foundEvent = this.getUpcomingEvents().stream()
                    .filter(event -> event.getId().equals(idEvent))
                    .findAny()
                    .orElse(null);
            if (foundEvent != null) {
                CollaboratorEntity foundCollab = this.collaboratorService.findById(idCollab);
                if (foundCollab != null) {
                    this.addCollabToEvent(foundCollab, foundEvent);
                    if (foundCollab.getId().equals(foundEvent.getCreator().getId())) {
                        //this.mailService.sendEventMailOrganizer(foundEvent, foundCollab);
                    } else {
                       //this.mailService.sendEventMailParticipant(foundEvent, foundCollab);
                    }
                }
            }
            LOGGER.info("end participate to event");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return foundEvent;

    }

    /**
     * cancel participation
     *
     * @param idEvent
     * @param idCollab
     * @return
     */
    @Override
    @Transactional
    public EventEntity cancelEvent(Long idEvent, Long idCollab) {
        EventEntity foundEvent = null;
        try {
            LOGGER.info("cancel event participation");
            foundEvent = this.getUpcomingEvents().stream()
                    .filter(event -> event.getId().equals(idEvent))
                    .findAny()
                    .orElse(null);
            if (foundEvent != null) {
                CollaboratorEntity foundCollab = foundEvent.getCollaborators().stream()
                        .filter(collab -> collab.getId().equals(idCollab))
                        .findAny()
                        .orElse(null);
                if (foundCollab != null) {
                    this.deleteCollabFromEvent(foundCollab, foundEvent);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return foundEvent;
    }


    /**
     * @param collab
     * @param event
     */
    public void deleteCollabFromEvent(CollaboratorEntity collab, EventEntity event) {
        LOGGER.info("up to date collaborator removed event");
        this.scoreService.decrement(collab.getId(), PARTICIPATE_EVENT_COMPONENT_LABEL);
        if (!collab.getEvents().isEmpty()) {
            collab.getEvents().remove(event);
            collaboratorService.save(collab);
        }

    }


    /**
     * @param collab
     * @param event
     */
    public void addCollabToEvent(CollaboratorEntity collab, EventEntity event) {
        LOGGER.info("up to date collaborator added event");
        this.scoreService.increment(collab.getId(), PARTICIPATE_EVENT_COMPONENT_LABEL);
        if (!collab.getEvents().isEmpty()) {
            collab.getEvents().add(event);
        } else {
            LOGGER.info("events  is null");
            Collection<EventEntity> events = new ArrayList<>();
            events.add(event);
            collab.setEvents(events);
        }
        collaboratorService.save(collab);
    }

    /**
     * add event
     *
     * @param event
     * @return
     */
    @Override
    public EventEntity addEvent(EventEntity event)  {
        EventEntity newEvent = this.eventRepository.save(event);
        this.scoreService.increment(event.getCreator().getId(), EVENT_COMPONENT_LABEL);
        return newEvent;
    }

    /**
     * get events
     *
     * @return
     */
    @Override
    public Collection<EventEntity> getUpcomingEvents() {

        return this.eventRepository.findUpcomingEvents();
    }

    @Override
    public Collection<EventEntity> getPastEvents() {
        return this.eventRepository.findPastEvents();
    }

    /**
     * *archive passed events
     */
    @Override
    public void archivePastEvent() {
        Collection<EventEntity> pastEventList = this.findActivePastEvents();
        if (!pastEventList.isEmpty()) {
            LOGGER.info("past event not empty");
            pastEventList.stream().map(event -> this.archiveByEvent(event)).collect(Collectors.toList());
        }
    }

    /**
     * get past events
     *
     * @return
     */
    @Override
    public Collection<EventEntity> findActivePastEvents() {
        return eventRepository.findActivePastEvents();
    }

    /**
     * @param event
     * @return
     */
    public EventEntity archiveByEvent(EventEntity event) {
        if (event != null) {
            event.setStatus(EventStatusEnum.ARCHIVED);
            this.eventRepository.save(event);
            LOGGER.info("set event status");

        }
        return event;
    }

    /**
     * archive past event
     */
    @Scheduled(cron = "${" + ARCHIVE_PAST_EVENT_CRON + "}", zone = TIME_ZONE_PARIS)
    public void archivePastEventJob() {
        JobHistoryEntity jobHistoryEntity = new JobHistoryEntity();
        //Job name
        jobHistoryEntity.setJobName("Archive Past Event");
        try {
            //job start
            LOGGER.info("start archive past event job");
            Instant jobStart = Instant.now();
            jobHistoryEntity.setStartDate(jobStart);
            //archive method
            this.archivePastEvent();
            //job finish
            Instant jobEnd = Instant.now();
            jobHistoryEntity.setEndDate(jobEnd);
            Duration between = Duration.between(jobStart, jobEnd);
            jobHistoryEntity.setJobDuration(between.getNano());
            jobHistoryEntity.setJobStatus(CommonConstants.JOB_STATUS_OK);
            this.jobHistoryService.saveJobHistory(jobHistoryEntity);
            LOGGER.info("end archive past event job");

        } catch (Exception e) {
            jobHistoryEntity.setJobStatus(CommonConstants.JOB_STATUS_KO);
            this.jobHistoryService.saveJobHistory(jobHistoryEntity);
            LOGGER.info("error in archive past event job");

        }

    }

    /**
     * get event by ID
     *
     * @param id
     * @return
     */
    @Override
    public EventEntity getEventById(Long id) {
        return this.eventRepository.findById(id).get();
    }

    @Override
    public void deleteEvent(Long idEvent, String deleteMotif) {
        LOGGER.info("delete event");
        EventEntity event = this.getEventById(idEvent);
        String creator = event.getCreator().getFirstName()+" "+ event.getCreator().getLastName();
        Collection<CollaboratorEntity> participantsInEvent = this.collaboratorService.findCollaboratorByEvent(idEvent);
        if(deleteMotif!=null) {
            event.setCanceledMotif(deleteMotif);
            this.eventRepository.save(event);
        }
        if(event!=null){
            LOGGER.info("Decrement score");
            this.scoreService.decrement(event.getCreator().getId(), EVENT_COMPONENT_LABEL);
            for(CollaboratorEntity collab: participantsInEvent){
                collab.getEvents().remove(event);
            }

            if(event.getIdEDMImage()!=null){
                LOGGER.info("delete participants");
                this.edmService.deleteFileFromEDM(event.getIdEDMImage());
            }


            this.eventRepository.deleteById(idEvent);
            for(CollaboratorEntity collab: participantsInEvent){
               /* LOGGER.info("send email to participant"+collab.getFirstName()+" "+collab.getLastName());
                 this.mailService.sendCanceledEventMailToParticipate(collab.getFirstName(),collab.getEmail(),event.getTitle(),
                         creator, deleteMotif);*/
            }
        }
    }

    @Override
    public EventEntity updateEvent(EventEntity event, Long id) {
        EventEntity eventSaved = new EventEntity();
        EventEntity eventExist = this.getEventById(id);
        if (eventExist != null) {
            eventExist.setTitle(event.getTitle());
            eventExist.setDate(event.getDate());
            eventExist.setNumberMaxParticipation(event.getNumberMaxParticipation());
            eventExist.setLocation(event.getLocation());
            eventExist.setType(event.getType());
            eventExist.setEventPrice(event.getEventPrice());
            eventExist.setOriginLink(event.getOriginLink());
            if (event.getIdEDMImage() != null) {
                eventExist.setIdEDMImage(event.getIdEDMImage());
                eventExist.setImageExtension(event.getImageExtension());
            }

            eventSaved = this.eventRepository.save(eventExist);
            Collection<CollaboratorEntity> participantsInEvent = this.collaboratorService.findCollaboratorByEvent(eventSaved.getId());
            String creator = eventSaved.getCreator().getFirstName() + " " + eventSaved.getCreator().getLastName();
            for (CollaboratorEntity collab : participantsInEvent) {
               /* LOGGER.info("send email to participant" + collab.getFirstName() + " " + collab.getLastName());
                this.mailService.sendUpdateEventMailToParticipate(collab.getFirstName(), collab.getEmail(), event.getTitle(),
                        creator);*/
            }
            return eventSaved;
        }
        return eventSaved;
    }
}
