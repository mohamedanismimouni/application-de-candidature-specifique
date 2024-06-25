package com.talan.polaris.dto;

import com.talan.polaris.entities.DepartmentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class offerDTO {
    private Long id ;
    /*private Instant createdAt;
    private Instant updatedAt;*/
    private String reference ;
    private String subject ;
    private String contexte ;
    private String description ;
    private DepartmentEntity department ;

}
