package com.talan.polaris.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignatoryDTO {
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private String firstName;
    private String lastName;
}
