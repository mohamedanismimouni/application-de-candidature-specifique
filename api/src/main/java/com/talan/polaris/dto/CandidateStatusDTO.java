package com.talan.polaris.dto;

import com.talan.polaris.enumerations.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateStatusDTO {
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private StatusEnum statusEnum;

}
