package com.talan.polaris.dto;

import com.talan.polaris.enumerations.RequestStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentResuestsStaticsDTO {
    private RequestStatusEnum status;
    private Long sum;
}
