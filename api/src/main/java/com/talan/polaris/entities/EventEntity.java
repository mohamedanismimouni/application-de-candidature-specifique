package com.talan.polaris.entities;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

import com.talan.polaris.enumerations.EventStatusEnum;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EVENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_event_entity")
    @SequenceGenerator(name = "seq_event_entity", sequenceName = "seq_event_entity", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    
    @CreatedDate
    @CreationTimestamp
    private Instant createdAt;
    
    @Column(name = "title")
    private String title;
	
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Column(name = "location")
    private String location;
    
    @Column(name = "type")
    private String type;

    @Column(name = "event_price")
    private Double eventPrice;
    
    @Column(name = "originLink")
    private String originLink;
    
    @JoinColumn(name = "CREATOR")
    @ManyToOne(fetch = FetchType.LAZY)
    private CollaboratorEntity creator;

    @Enumerated(EnumType.STRING)
    private EventStatusEnum status;

    @Column(nullable = true)
    private Long idEDMImage;

    @Column(nullable = true)
    private String imageExtension;

    private Integer numberMaxParticipation;


    @ManyToMany(mappedBy = "events")
    private Collection<CollaboratorEntity> collaborators;

    @Column(nullable = true)
    private String canceledMotif;

}
