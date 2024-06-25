package com.talan.polaris.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsefulDocumentDTO {
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private String label;
    private Date uploadDate;
    private Long idEDM;
    private byte[] thumbnail;

}
