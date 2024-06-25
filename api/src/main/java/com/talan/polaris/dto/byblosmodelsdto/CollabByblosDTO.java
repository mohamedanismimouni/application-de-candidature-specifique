package com.talan.polaris.dto.byblosmodelsdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollabByblosDTO {

    private Long id  ;
    private String civility ;
    private String matricule ;
    private String lastName ;
    private String firstName ;
    private String compagny ;
    private String birthday ;
    private String mailPro ;
    private String telPro ;
    private String contractType ;
    private Date hiringDate ;
    private Date endContractDate ;
    private String endTestPeriodDate ;
    private String renewedTestPeriod ;
    private String function ;
    private String pole ;
    private String manager ;
    private String managerMatricule ;
    private String managerMail ;
    private String domain ;
    private String position ;
    private String prj ;
    private String ifrTr ;
    private String communaute ;
    private SalaryHistByblosDTO[] salaryHist;

}
