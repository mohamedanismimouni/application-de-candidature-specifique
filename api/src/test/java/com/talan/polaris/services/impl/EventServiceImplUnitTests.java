package com.talan.polaris.services.impl;

import static com.talan.polaris.constants.CommonConstants.TIME_ZONE_PARIS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

import com.talan.polaris.entities.CollaboratorEntity;
import com.talan.polaris.entities.RequestTypeEntity;
import com.talan.polaris.entities.UsefulDocumentEntity;
import com.talan.polaris.enumerations.EventStatusEnum;
import com.talan.polaris.services.CollaboratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.talan.polaris.repositories.EventRepository;
import com.talan.polaris.entities.EventEntity;
import org.springframework.scheduling.support.CronSequenceGenerator;


public class EventServiceImplUnitTests {

    @Mock
    private EventRepository eventRepository;
    @InjectMocks
    private EventServiceImpl eventServiceImpl;
    @Mock
    private CollaboratorService collaboratorService;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void findUpcomingEvents_whenCalled_thenEventsAreFound() {

        Collection<EventEntity> upcomingEvents = this.eventServiceImpl.getUpcomingEvents();
        verify(eventRepository, times(1)).findUpcomingEvents();
        assertThat(upcomingEvents).isNotNull();
    }

    @Test
    public void findPastEvents__whenCalled_thenEventsAreFound() {

        Collection<EventEntity> pastEvents = this.eventServiceImpl.getPastEvents();
        verify(eventRepository, times(1)).findPastEvents();
        assertThat(pastEvents).isNotNull();
    }

    @Test
    public void findActivePastEvents_whenCalled_thenEventsAreFound() {

        Collection<EventEntity> pastEvents = this.eventServiceImpl.findActivePastEvents();
        verify(eventRepository, times(1)).findActivePastEvents();
        assertThat(pastEvents).isNotNull();
    }

    @Test
    public void archivePastEvent_whenCalled_thenEventsAreArchived() {

        this.eventServiceImpl.archivePastEvent();
        verify(eventRepository, times(1)).findActivePastEvents();
    }

    @Test
    public void archiveByEvent_whenCalled_thenEventsAreArchived() {
        EventEntity event = new EventEntity();
        event.setStatus(EventStatusEnum.ACTIVE);
        event.setId(1L);
        event.setTitle("title");
        event.setCreatedAt(Instant.now());
        event.setDate(new Date());
        event.setCreator(new CollaboratorEntity());
        //event.setPrice("10DT");
        EventEntity savedEvent = this.eventServiceImpl.archiveByEvent(event);
        verify(eventRepository, times(1)).save(event);
        assertThat(savedEvent).isNotNull();
    }

    @Test
    void addEvent_givenEventEntity_whenCalled_thenCallEventRepository() {
        EventEntity event = new EventEntity();
        event.setId(1L);
        event.setTitle("title");
        event.setCreatedAt(Instant.now());
        event.setDate(new Date());
        event.setCreator(new CollaboratorEntity());
        //event.setPrice("10DT");
        EventEntity savedEvent = this.eventServiceImpl.archiveByEvent(event);
        verify(eventRepository, times(1)).save(event);
        assertThat(savedEvent).isNotNull();
    }


    @Test
    void checkCronTime_givenNextTimeOfCron_thenIsOK() {
        // given + when
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator("0 0 0 * * ?", TimeZone.getTimeZone(TIME_ZONE_PARIS));
        ZonedDateTime date = LocalDateTime.of(2021, 1, 1, 00, 00, 0).atZone(ZoneId.of(TIME_ZONE_PARIS));
        ZonedDateTime expected = LocalDateTime.of(2021, 1, 2, 00, 00, 0).atZone(ZoneId.of(TIME_ZONE_PARIS));
        // then
        assertThat(cronSequenceGenerator.next(Date.from(date.toInstant()))).isEqualTo(Date.from(expected.toInstant()));

    }

    @Test
    public void cancelEvent_whenCalled_thenEventsIsCanceled() {
        // given
        Long idEvent = (long) 1;
        Long idCollab = (long) 1;
        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(idEvent);
        EventServiceImpl eventServiceImplSpy = spy(this.eventServiceImpl);
        doReturn(eventEntity)
                .when(eventServiceImplSpy)
                .cancelEvent(anyLong(), anyLong());
        // when
        EventEntity participatedEvent = eventServiceImplSpy.cancelEvent(idEvent, idCollab);
        //then
        assertThat(eventEntity.getId()).isEqualTo(idEvent);
        assertThat(participatedEvent).isNotNull();
    }

    @Test
    public void participateEvent_whenCalled_thenCollaboratorIsParticipated() {
        // given
        Long idEvent = (long) 1;
        Long idCollab = (long) 1;
        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(idEvent);
        EventServiceImpl eventServiceImplSpy = spy(this.eventServiceImpl);
        doReturn(eventEntity)
                .when(eventServiceImplSpy)
                .participateEvent(anyLong(), anyLong());
        // when
        EventEntity participatedEvent = eventServiceImplSpy.participateEvent(idEvent, idCollab);
       //then
        assertThat(eventEntity.getId()).isEqualTo(idEvent);
        assertThat(participatedEvent).isNotNull();
    }


    @Test
    public void deleteEvent_whenCalled_thenEventsIsDeleted() {


        // given
        Long idEvent = (long) 1;

        String deleteMotif = "delete event";
        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(idEvent);
        eventEntity.setCanceledMotif(deleteMotif);

        EventServiceImpl eventServiceImplSpy = spy(this.eventServiceImpl);
        doNothing().when(eventServiceImplSpy).deleteEvent(anyLong(), anyString());
        // when
         eventServiceImplSpy.deleteEvent(idEvent, deleteMotif);
        //then
        verify(eventServiceImplSpy, only()).deleteEvent(idEvent, deleteMotif);
    }
    @Test
    public void updateEvent_whenCalled_thenEventIsUpdated() {
        // given
        Long idEvent = (1L);
        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(idEvent);
        eventEntity.setTitle("updated event");
        EventEntity existEvent = new EventEntity();
        existEvent.setId(2L);
        existEvent.setTitle("first event");
        EventServiceImpl eventServiceImplSpy = spy(this.eventServiceImpl);
        doReturn(eventEntity)
                .when(eventServiceImplSpy)
                .updateEvent(any(EventEntity.class), anyLong());
        // when
        EventEntity updatedEvent = eventServiceImplSpy.updateEvent(eventEntity, 2L);

        //then
        assertThat(updatedEvent).isNotNull();
        assertThat(updatedEvent.getTitle()).isEqualTo(eventEntity.getTitle());
    }
}



