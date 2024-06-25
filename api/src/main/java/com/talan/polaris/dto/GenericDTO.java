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
public class GenericDTO {
    private String id;
    private Instant createdAt;
    private Instant updatedAt;

    @Override
    public String toString() {

        return new StringBuilder()
                .append("id                = ").append(this.getId()).append("\n")
                .append("createdAt         = ").append(this.getCreatedAt()).append("\n")
                .append("updatedAt         = ").append(this.getUpdatedAt())
                .toString();

    }


}
