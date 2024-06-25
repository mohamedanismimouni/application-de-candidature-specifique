package com.talan.polaris.dto;

import com.talan.polaris.enumerations.ProfileTypeEnum;
import lombok.*;

import java.time.Instant;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfileTypeDTO {
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private ProfileTypeEnum label;
}
