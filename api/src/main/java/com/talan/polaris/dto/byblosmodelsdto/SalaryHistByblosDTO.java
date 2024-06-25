package com.talan.polaris.dto.byblosmodelsdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryHistByblosDTO {

    private Date beginDate ;
    private Date endDate ;
    private String qualification ;
    private String sousQualification ;
    private Double monthlySalary  ;
    private Double variableSalary  ;


}
