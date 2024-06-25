package com.talan.polaris.dto;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;

import com.talan.polaris.enumerations.EventStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {

    private Long id;
    private Instant createdAt;
    private String title;
    private Date date;
    private String location;
    private String originLink;
    private CollaboratorDTO creator;
    private Long idEDMImage;
    private String imageExtension;
    private EventStatusEnum status;
    private Collection<CollaboratorDTO> collaborators;
    private Integer numberMaxParticipation;
    private String canceledMotif;
    private String type;
    private Double eventPrice;




}
