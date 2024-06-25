package com.talan.polaris.services.impl;

import com.talan.polaris.common.EncryptCommonMethod;
import com.talan.polaris.constants.CommonConstants;
import com.talan.polaris.entities.CollaboratorEntity;
import com.talan.polaris.entities.JobHistoryEntity;
import com.talan.polaris.entities.RequestTypeEntity;
import com.talan.polaris.entities.SalaryHistoryEntity;
import com.talan.polaris.enumerations.DateTypeEnum;
import com.talan.polaris.enumerations.RequestTypeEnum;
import com.talan.polaris.enumerations.SalaryTypeEnum;
import com.talan.polaris.services.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.time.*;
import java.util.Collection;
import java.util.Date;
import static com.talan.polaris.constants.CommonConstants.*;
import static com.talan.polaris.constants.ConfigurationConstants.*;

/**
 * An implementation of {@link PayRollService}, containing business methods
 *
 * @author Imen Mechergui
 * @since 1.0.0
 */
@Service
@EnableScheduling
public class PayRollServiceImpl implements PayRollService {

    private final CollaboratorService collabService;
    private final RequestTypeService requestTypeService;
    private final SalaryHistoryService salaryHistoryService;
    private final JobHistoryService jobHistoryService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PayRollServiceImpl.class);


    @Autowired
    public PayRollServiceImpl(CollaboratorService collabService,
                              SalaryHistoryService salaryHistoryService,
                              JobHistoryService jobHistoryService,
                              RequestTypeService requestTypeService) {
        this.collabService = collabService;
        this.salaryHistoryService = salaryHistoryService;
        this.jobHistoryService = jobHistoryService;
        this.requestTypeService = requestTypeService;
    }


    /**
     * upload reminder file
     *
     * @param file
     * @param reminderUploadDate
     * @throws IOException
     */
    @Override
    @Transactional
    public void processExcelReminderFile(MultipartFile file, Date reminderUploadDate) throws IOException {
        try {
            LOGGER.info("start process reminder");

            //loading Excel File
            XSSFSheet worksheet = this.workSheet(file);
            //browse file by line
            if (worksheet.getPhysicalNumberOfRows() > 0) {
                this.processReminderByRow(worksheet, reminderUploadDate);

            } else {
                LOGGER.error("empty PayRoll File");
                throw new IllegalArgumentException("Empty File Exception");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        saveUploadDate(RequestTypeEnum.REMINDER);
    }

    /**
     * @param worksheet
     * @param reminderUploadDate
     */
    public void processReminderByRow(XSSFSheet worksheet, Date reminderUploadDate) {
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = worksheet.getRow(i);
            if (row != null) {
                LOGGER.info("row not null");
                if (row.getCell(0) != null && row.getCell(1) != null && row.getCell(2) != null && row.getCell(3) != null && row.getCell(0).getCellType() != CellType.BLANK && row.getCell(1).getCellType() != CellType.BLANK && row.getCell(2).getCellType() != CellType.BLANK && row.getCell(3).getCellType() != CellType.BLANK) {
                    String matricule = null;
                    Double rappel = null;
                    //casting registration number
                    matricule = this.getRegistrationNumber(row);
                    //validate reminder type value
                    if (row.getCell(3).getCellType().toString().equals(NUMERIC_TYPE)) {
                        LOGGER.info("reminder is valid");
                        rappel = row.getCell(3).getNumericCellValue();
                        //process reminder by exist collab
                        this.processReminderByCollab(matricule, reminderUploadDate, rappel);
                    }
                }
            }
        }
    }

    /**
     * get Registration Number
     * @param row
     * @return
     */
    public String getRegistrationNumber(XSSFRow row) {
        String matricule = null;
        if (row.getCell(0).getCellType().toString().equals(NUMERIC_TYPE)) {
            Double matriculeLong = row.getCell(0).getNumericCellValue();
            int convertMatriculeToInt = (int) Math.round(matriculeLong);
            matricule = Integer.toString(convertMatriculeToInt);
        } else {
            if (!row.getCell(0).getStringCellValue().isEmpty() || !row.getCell(0).getStringCellValue().isBlank()) {
                matricule = row.getCell(0).getStringCellValue().trim();
            }
        }
        return matricule;
    }

    /**
     * process by collab
     *
     * @param matricule
     * @param reminderUploadDate
     * @param rappel
     */
    public void processReminderByCollab(String matricule, Date reminderUploadDate, Double rappel) {
        //get Collaborator by Matricule
        CollaboratorEntity collab = collabService.findCollabByMatricule(matricule);
        //Collaborator Exist
        if (collab != null) {
            LOGGER.info("collab exist");
            SalaryHistoryEntity currentMonthSalary = salaryHistoryService.getSalaryHistoryByMonth(collab.getId(), reminderUploadDate);
            //history is created
            if (currentMonthSalary != null) {
                currentMonthSalary.setEncryptedReminder(EncryptCommonMethod.encryptDouble(rappel));
                salaryHistoryService.saveSalaryHistory(currentMonthSalary);
            } else {
                //create new history
                createNewHistoryReminderEntity(collab, rappel, reminderUploadDate, true);
            }

        }

    }


    /**
     * upload payroll file
     *
     * @param file
     * @param payrollUploadDate
     */
    @Override
    @Transactional
    public void processExcelPayrollFile(MultipartFile file, Date payrollUploadDate) {
        try {
            LOGGER.info("start process payroll");
            //loading excel file
            XSSFSheet worksheet = this.workSheet(file);
            //browse file by line
            if (worksheet.getPhysicalNumberOfRows() > 0) {
                this.processPayrollByRow(worksheet, payrollUploadDate);

            } else {
                LOGGER.error("empty PayRoll File");
                throw new IllegalArgumentException("Empty File Exception");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //save Upload date
        saveUploadDate(RequestTypeEnum.PAYROLL);
    }


    /**
     * @param worksheet
     * @param payrollUploadDate
     */
    public void processPayrollByRow(XSSFSheet worksheet, Date payrollUploadDate) {
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            String matricule = null;
            Double netSalary = null;
            Double baseSalary = null;
            Double bonus = null;
            //get  file Row
            XSSFRow row = worksheet.getRow(i);
            if (row != null) {
                LOGGER.info("row not  null");

                if (row.getCell(0) != null && row.getCell(1) != null && row.getCell(2) != null && row.getCell(3) != null && row.getCell(4) != null && row.getCell(5) != null && row.getCell(0).getCellType() != CellType.BLANK && row.getCell(1).getCellType() != CellType.BLANK && row.getCell(2).getCellType() != CellType.BLANK && row.getCell(3).getCellType() != CellType.BLANK && row.getCell(4).getCellType() != CellType.BLANK && row.getCell(5).getCellType() != CellType.BLANK) {
                    //casting Registration Number
                    matricule = this.getRegistrationNumber(row);
                    //validate salaries values
                    if (row.getCell(3).getCellType().toString().equals(NUMERIC_TYPE) && row.getCell(4).getCellType().toString().equals(NUMERIC_TYPE) && row.getCell(5).getCellType().toString().equals(NUMERIC_TYPE)) {
                        LOGGER.info("Valid salaries values");
                        baseSalary = row.getCell(3).getNumericCellValue();
                        netSalary = row.getCell(4).getNumericCellValue();
                        bonus = row.getCell(5).getNumericCellValue();
                        //process payroll by exist collab
                        this.processPayRollByCollab(matricule, payrollUploadDate, baseSalary, netSalary, bonus);
                    }

                }
            }

        }

    }


    /**
     * @param matricule
     * @param payrollUploadDate
     * @param baseSalary
     * @param netSalary
     * @param bonus
     */
    public void processPayRollByCollab(String matricule, Date payrollUploadDate, Double baseSalary, Double netSalary, Double bonus) {
        CollaboratorEntity collab = collabService.findCollabByMatricule(matricule);
        //Collaborator Exist
        if (collab != null) {
            LOGGER.info("collab exists");
            SalaryHistoryEntity currentMonthSalary = salaryHistoryService.getSalaryHistoryByMonth(collab.getId(), payrollUploadDate);
            //history is created by the job
            if (currentMonthSalary != null) {
                //new values
                if (!EncryptCommonMethod.decryptStringToDouble(currentMonthSalary.getEncryptedBaseSalary()).equals(baseSalary)) {

                    //base
                    currentMonthSalary.setEncryptedBaseSalary(EncryptCommonMethod.encryptDouble(baseSalary));

                    currentMonthSalary.setSalaryUpdatedAt(Instant.now());
                }
                if (!EncryptCommonMethod.decryptStringToDouble(currentMonthSalary.getEncryptedNetSalary()).equals(netSalary)) {
                    //net
                    currentMonthSalary.setEncryptedNetSalary(EncryptCommonMethod.encryptDouble(netSalary));
                    currentMonthSalary.setSalaryUpdatedAt(Instant.now());
                }
                if (!EncryptCommonMethod.decryptStringToDouble(currentMonthSalary.getEncryptedBonus()).equals(bonus)) {
                    //bonus
                    currentMonthSalary.setEncryptedBonus(EncryptCommonMethod.encryptDouble(bonus));
                }
                currentMonthSalary.setPayRollDate(payrollUploadDate);
                currentMonthSalary.setUpdatedByBO(true);
                salaryHistoryService.saveSalaryHistory(currentMonthSalary);

            } else {
                //create new
                createNewHistoryEntity(collab, netSalary, baseSalary, bonus, ZERO_VALUE, payrollUploadDate, true);
            }
        }
    }


    /**
     * loading EXCEL file
     *
     * @param file
     * @return
     * @throws IOException
     */
    public XSSFSheet workSheet(MultipartFile file) throws IOException {
        saveUploadDate(RequestTypeEnum.REMINDER);
        InputStream is = file.getInputStream();
        //workBook
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        return workbook.getSheetAt(0);
    }


    /**
     * save date Upload
     * @param requestType
     */
    public void saveUploadDate(RequestTypeEnum requestType) {
        RequestTypeEntity payRollRequest = requestTypeService.getTypeByLabel(requestType);
        payRollRequest.setUploadDate(new Date());
        requestTypeService.update(payRollRequest);
    }

    /**
     * initialization Salary Job
     */
    @Scheduled(cron = "${" + INI_SALA_CRON + "}", zone = TIME_ZONE_PARIS)
    public void initializationSalaryJob() {
        JobHistoryEntity jobHistoryEntity = new JobHistoryEntity();
        //Job name
        jobHistoryEntity.setJobName("initialization Salary for all collaborators");
        try {
            //job start
            Instant jobStart = Instant.now();
            jobHistoryEntity.setStartDate(jobStart);
            Collection<CollaboratorEntity> collabList = collabService.findAll();
            //initialize
            if (!collabList.isEmpty()) {
                this.initializeCollab(collabList);
            }
            //job finish
            Instant jobEnd = Instant.now();
            jobHistoryEntity.setEndDate(jobEnd);
            Duration between = Duration.between(jobStart, jobEnd);
            jobHistoryEntity.setJobDuration(between.getNano());
            jobHistoryEntity.setJobStatus(CommonConstants.JOB_STATUS_OK);
            this.jobHistoryService.saveJobHistory(jobHistoryEntity);

        } catch (Exception e) {
            jobHistoryEntity.setJobStatus(CommonConstants.JOB_STATUS_KO);
            this.jobHistoryService.saveJobHistory(jobHistoryEntity);
        }
    }

    /**
     * initialize collaborators Salaries
     *
     * @param collabList
     */
    public void initializeCollab(Collection<CollaboratorEntity> collabList) {

        for (
                CollaboratorEntity collab : collabList) {
            //get current month salary
            SalaryHistoryEntity currentMonthSalary = salaryHistoryService.getSalaryHistoryByMonth(collab.getId(), getDateByType(DateTypeEnum.CURRENT));
            //current month salary not exist
            if (currentMonthSalary == null) {
                Date lastUpdatedDate = salaryHistoryService.getLastUpdatedSalaryByCollab(collab.getId());
                if (lastUpdatedDate != null) {
                    SalaryHistoryEntity lastHistory = salaryHistoryService.getHistoryByDate(lastUpdatedDate, collab.getId());
                    if (lastHistory != null) {
                        //bonus+reminder =>0
                        createNewHistoryEntity(collab,  EncryptCommonMethod.decryptStringToDouble(lastHistory.getEncryptedNetSalary()), EncryptCommonMethod.decryptStringToDouble(lastHistory.getEncryptedBaseSalary()), ZERO_VALUE, ZERO_VALUE, getDateByType(DateTypeEnum.CURRENT), false);
                    }
                } else {
                    //create history salary + reminder+bonus=>0
                    createNewHistoryEntity(collab, ZERO_VALUE, ZERO_VALUE, ZERO_VALUE, ZERO_VALUE, getDateByType(DateTypeEnum.CURRENT), false);
                }
            }
        }


    }

    /**
     * rappel annuaire
     *
     * @param collab
     * @return
     */
    public Double annualReminder(CollaboratorEntity collab) {
        Double annualReminder = 0.0;
        Collection<SalaryHistoryEntity> getAllSalariesHistories = salaryHistoryService.getSalaryHistoryByCollab(collab.getId(), getDateByType(DateTypeEnum.FIRST), getDateByType(DateTypeEnum.END));
        if (!getAllSalariesHistories.isEmpty()) {
            for (SalaryHistoryEntity history : getAllSalariesHistories) {
                if(history.getEncryptedReminder()!=null)
                {    annualReminder = annualReminder + EncryptCommonMethod.decryptStringToDouble(history.getEncryptedReminder());


                }
            }
        }
        return annualReminder;
    }


    /**
     * @param collab
     * @param salaryEnum
     * @return
     */
    @Override
    public Double annualSalary(CollaboratorEntity collab, SalaryTypeEnum salaryEnum) {
        double annualSalary = 0;
        //get all Salaries Histories for collab
        Collection<SalaryHistoryEntity> getAllSalariesHistories = salaryHistoryService.getSalaryHistoryByCollab(collab.getId(), getDateByType(DateTypeEnum.FIRST), getDateByType(DateTypeEnum.END));
        //get current month salary
        SalaryHistoryEntity currentMonthSalary = salaryHistoryService.getSalaryHistoryByMonth(collab.getId(), getDateByType(DateTypeEnum.CURRENT));
        //calculate
        if(!getAllSalariesHistories.isEmpty()) {
            annualSalary = this.calculateAnnualSalaryByType(getAllSalariesHistories, salaryEnum, currentMonthSalary, collab);
        }
        return annualSalary;
    }

    /**
     *
     * @param getAllSalariesHistories
     * @param salaryEnum
     * @param currentMonthSalary
     * @param collab
     * @return
     */
    public Double calculateAnnualSalaryByType(Collection<SalaryHistoryEntity> getAllSalariesHistories, SalaryTypeEnum salaryEnum, SalaryHistoryEntity currentMonthSalary, CollaboratorEntity collab) {
        Double annualSalary = 0.0;
        //next months without slaries
        int currentMonth = LocalDate.now().getMonthValue();
        int nextMonths = 12 - currentMonth;
           //Salary Type
            if (salaryEnum.equals(SalaryTypeEnum.BASE)) {
                for (SalaryHistoryEntity history : getAllSalariesHistories) {
                    annualSalary = annualSalary + EncryptCommonMethod.decryptStringToDouble(history.getEncryptedBaseSalary());
                }
                annualSalary = annualSalary + annualReminder(collab);
                if (currentMonthSalary != null) {
                    annualSalary = annualSalary + (nextMonths *  EncryptCommonMethod.decryptStringToDouble(currentMonthSalary.getEncryptedBaseSalary()));
                }
            } else if (salaryEnum.equals(SalaryTypeEnum.NET)) {
                for (SalaryHistoryEntity history : getAllSalariesHistories) {
                    annualSalary = annualSalary +  EncryptCommonMethod.decryptStringToDouble(history.getEncryptedNetSalary());
                }
                if (currentMonthSalary != null) {
                    annualSalary = annualSalary + (nextMonths * EncryptCommonMethod.decryptStringToDouble(currentMonthSalary.getEncryptedNetSalary()));
                }
            } else if (salaryEnum.equals(SalaryTypeEnum.BONUS)) {
                for (SalaryHistoryEntity history : getAllSalariesHistories) {
                    annualSalary = annualSalary + EncryptCommonMethod.decryptStringToDouble(history.getEncryptedBonus());
                }
            }

        return annualSalary;
    }




    /**
     * get date by type
     *
     * @param dateType
     * @return
     */
    @Override
    public Date getDateByType(DateTypeEnum dateType) {
        LocalDate localDate = LocalDate.now();
        LocalDate localdateType = null;
        if (dateType.equals(DateTypeEnum.CURRENT)) {
            localdateType = LocalDate.of(localDate.getYear(), localDate.getMonthValue(), 01);

        } else if (dateType.equals(DateTypeEnum.FIRST)) {
            localdateType = LocalDate.of(localDate.getYear(), 01, 01);

        } else if (dateType.equals(DateTypeEnum.END)) {
            localdateType = LocalDate.of(localDate.getYear(), 12, 31);
        }

        ZonedDateTime zdt = localdateType.atStartOfDay(ZoneId.systemDefault());
        Instant instant = zdt.toInstant();

        return Date.from(instant);
    }

    /**
     * create new History
     *
     * @param collab
     * @param netSalary
     * @param baseSalary
     * @return
     */
    public SalaryHistoryEntity createNewHistoryEntity(CollaboratorEntity collab, Double netSalary, Double
            baseSalary, Double bonus, Double reminder, Date payrollUploadDate, Boolean updatedByBO) {
        SalaryHistoryEntity salaryHistory = new SalaryHistoryEntity();
        //encrypted values
        salaryHistory.setEncryptedBaseSalary(EncryptCommonMethod.encryptDouble(baseSalary));
        salaryHistory.setEncryptedNetSalary(EncryptCommonMethod.encryptDouble(netSalary));
        salaryHistory.setEncryptedReminder(EncryptCommonMethod.encryptDouble(reminder));
        salaryHistory.setEncryptedBonus(EncryptCommonMethod.encryptDouble(bonus));
        salaryHistory.setPayRollDate(payrollUploadDate);
        salaryHistory.setCollaborator(collab);
        salaryHistory.setSalaryUpdatedAt(Instant.now());
        salaryHistory.setUpdatedByBO(updatedByBO);
        return salaryHistoryService.saveSalaryHistory(salaryHistory);
    }


    /**
     *
     * @param collab
     * @param reminder
     * @param payrollUploadDate
     * @param updatedByBO
     * @return
     */
    public SalaryHistoryEntity createNewHistoryReminderEntity(CollaboratorEntity collab, Double reminder,
                                                              Date payrollUploadDate, Boolean updatedByBO) {
        SalaryHistoryEntity saveSalary = null;
        Date lastUpdatedDate = salaryHistoryService.getLastUpdatedSalaryByCollab(collab.getId());
        if (lastUpdatedDate != null) {
            SalaryHistoryEntity lastHistory = salaryHistoryService.getHistoryByDate(lastUpdatedDate, collab.getId());
            if (lastHistory != null) {
                saveSalary = createNewHistoryEntity(collab, EncryptCommonMethod.decryptStringToDouble(lastHistory.getEncryptedNetSalary()), EncryptCommonMethod.decryptStringToDouble(lastHistory.getEncryptedBaseSalary()), ZERO_VALUE, reminder, payrollUploadDate, updatedByBO);
            }
        } else {
            //create history with NULL salary
            saveSalary = createNewHistoryEntity(collab, ZERO_VALUE, ZERO_VALUE, ZERO_VALUE, reminder, payrollUploadDate, false);
        }
        return saveSalary;
    }


}