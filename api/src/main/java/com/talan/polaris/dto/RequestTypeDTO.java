package com.talan.polaris.dto;
import com.talan.polaris.enumerations.RequestTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestTypeDTO{
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private RequestTypeEnum label;
    private Date uploadDate;
    private Long idEDM;
    private Boolean visibility;
    private SignatoryDTO signatory;
    private Boolean isTemplateOfRequest;



}
