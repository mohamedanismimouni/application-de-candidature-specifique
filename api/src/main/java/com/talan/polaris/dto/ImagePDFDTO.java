package com.talan.polaris.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImagePDFDTO {
    private String thumbnail;
    private Long idEDM;
    private String label;
}
