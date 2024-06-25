package com.talan.polaris.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsefulLinkDTO {
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private String label;
    private String link;
}