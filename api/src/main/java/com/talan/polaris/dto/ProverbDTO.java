package com.talan.polaris.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProverbDTO {
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private String text;
    private Boolean bySystem;
    private String author;
    private CollaboratorDTO creator;
    private Boolean published;



}
