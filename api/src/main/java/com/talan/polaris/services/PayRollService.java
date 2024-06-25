package com.talan.polaris.services;

import com.talan.polaris.entities.CollaboratorEntity;
import com.talan.polaris.enumerations.DateTypeEnum;
import com.talan.polaris.enumerations.SalaryTypeEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;


/**
 * PayRollService.
 *
 * @author Imen Mechergui
 * @since 1.0.0
 */
public interface PayRollService {

    public void processExcelPayrollFile(MultipartFile file,Date payrollUploadDate);
    public Double annualSalary(CollaboratorEntity collab, SalaryTypeEnum salaryEnum) ;
    public Date getDateByType(DateTypeEnum dateType) ;
    public void processExcelReminderFile(MultipartFile file, Date reminderUploadDate) throws IOException ;



    }


