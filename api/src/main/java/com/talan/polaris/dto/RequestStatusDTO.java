package com.talan.polaris.dto;

import com.talan.polaris.enumerations.RequestStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestStatusDTO{
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private RequestStatusEnum label;
}
