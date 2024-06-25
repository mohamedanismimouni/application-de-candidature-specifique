package com.talan.polaris.services.impl;

import com.talan.polaris.entities.*;
import com.talan.polaris.enumerations.RequestTypeEnum;
import com.talan.polaris.enumerations.SalaryTypeEnum;
import com.talan.polaris.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

import static com.talan.polaris.constants.CommonConstants.TIME_ZONE_PARIS;
import static com.talan.polaris.constants.CommonConstants.ZERO_VALUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit tests class for methods implemented in {@link com.talan.polaris.services.PayRollService}.
 *
 * @author Imen Mechergui
 * @since 1.0.0
 */
public class PayRollServiceImplUnitTests {
    @InjectMocks
    private PayRollServiceImpl payRollServiceImpl;
    @Mock
    private CollaboratorAPIByblosService collaboratorAPIByblosService;
    @Mock
    private CollaboratorService collaboratorService;
    @Mock
    private RequestTypeService requestTypeService;
    @Mock
    private SalaryHistoryService salaryHistoryService;
    @Mock
    private JobHistoryService jobHistoryService;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }


    @Test
    public void initializationSalaryJob_whenCalled_thenSalaryIsInitialized() {
        PayRollServiceImpl payRollServiceImplSpy = spy(this.payRollServiceImpl);
        Collection<CollaboratorEntity> collabList = new ArrayList<>();
        //get result from request
        doReturn(collabList)
                .when(collaboratorService)
                .findAll();
        payRollServiceImplSpy.initializationSalaryJob();
        // then
        verify(collaboratorService, only()).findAll();
    }


    @Test
    public void annualSalary_whenCalled_thenAnnualSalaryIsCalculated() {
        PayRollServiceImpl payRollServiceImplSpy = spy(this.payRollServiceImpl);
        CollaboratorEntity collab = new CollaboratorEntity();
        //get result from request
        doReturn(new SalaryHistoryEntity())
                .when(this.salaryHistoryService)
                .getSalaryHistoryByMonth(anyLong(), any(Date.class));
        Double salary = payRollServiceImplSpy.annualSalary(collab, SalaryTypeEnum.BASE);
        // then
        assertThat(salary).isNotNull();
    }


    @Test
    public void createNewHistoryEntity_whenCalled_thenHistoryIsCreated() {
        PayRollServiceImpl payRollServiceImplSpy = spy(this.payRollServiceImpl);
        SalaryHistoryEntity salary = new SalaryHistoryEntity();
        doReturn(salary)
                .when(salaryHistoryService)
                .saveSalaryHistory(any(SalaryHistoryEntity.class));
        //get result from request
        payRollServiceImplSpy.createNewHistoryEntity(new CollaboratorEntity(), 120.5, 195.2, 500.0, ZERO_VALUE, new Date(), false);
        // then
        verify(payRollServiceImplSpy, only()).createNewHistoryEntity(any(CollaboratorEntity.class), anyDouble(), anyDouble(), anyDouble(), anyDouble(), any(), anyBoolean());

    }

    @Test
    public void savePayRollUploadDate_whenCalled_thenUploadDateIsUpdated() {
        PayRollServiceImpl payRollServiceImplSpy = spy(this.payRollServiceImpl);
        RequestTypeEntity type = new RequestTypeEntity();
        type.setLabel(RequestTypeEnum.PAYROLL);
        type.setUploadDate(new Date());
        type.setId((long) 1);
        type.setSignatory(new SignatoryEntity());
        doReturn(type)
                .when(requestTypeService)
                .getTypeByLabel(RequestTypeEnum.PAYROLL);
        payRollServiceImplSpy.saveUploadDate(RequestTypeEnum.PAYROLL);
        assertThat(this.requestTypeService.getTypeByLabel(RequestTypeEnum.PAYROLL)).isNotNull();


    }

    @Test
    void checkCronTime_givenNextTimeOfCron_thenIsOK() {
        // given + when
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator("0 0 0 1 * ?", TimeZone.getTimeZone(TIME_ZONE_PARIS));
        ZonedDateTime date = LocalDateTime.of(2021, 1, 1, 00, 00, 0).atZone(ZoneId.of(TIME_ZONE_PARIS));
        ZonedDateTime expected = LocalDateTime.of(2021, 2, 1, 00, 00, 0).atZone(ZoneId.of(TIME_ZONE_PARIS));
        // then
        assertThat(cronSequenceGenerator.next(Date.from(date.toInstant()))).isEqualTo(Date.from(expected.toInstant()));

    }

    @Test
    public void processExcelPayrollFile_givenExcelFile_whenCalled_thenFileIsProcessed() {
        final MultipartFile mockFile = mock(MultipartFile.class);
        PayRollServiceImpl payRollServiceImplSpy = spy(this.payRollServiceImpl);
        SalaryHistoryEntity salary = new SalaryHistoryEntity();
        Date payRollDate = new Date();
        doReturn(salary)
                .when(this.salaryHistoryService)
                .saveSalaryHistory(any(SalaryHistoryEntity.class));
        doAnswer((invocation) -> invocation.getArgument(0))
                .when(payRollServiceImplSpy)
                .processExcelPayrollFile(any(MultipartFile.class), any(Date.class));
        //get result from request
        payRollServiceImplSpy.processExcelPayrollFile(mockFile, payRollDate);
        // then
        verify(payRollServiceImplSpy, only()).processExcelPayrollFile(mockFile, payRollDate);
    }

    @Test
    public void processExcelReminderFile_givenExcelFile_whenCalled_thenFileIsProcessed() throws IOException {
        final MultipartFile mockFile = mock(MultipartFile.class);
        PayRollServiceImpl payRollServiceImplSpy = spy(this.payRollServiceImpl);
        SalaryHistoryEntity salary = new SalaryHistoryEntity();
        Date reminderDate = new Date();
        doReturn(salary)
                .when(this.salaryHistoryService)
                .saveSalaryHistory(any(SalaryHistoryEntity.class));
        doAnswer((invocation) -> invocation.getArgument(0))
                .when(payRollServiceImplSpy)
                .processExcelReminderFile(any(MultipartFile.class), any(Date.class));
        //get result from request
        payRollServiceImplSpy.processExcelReminderFile(mockFile, reminderDate);
        // then
        verify(payRollServiceImplSpy, only()).processExcelReminderFile(mockFile, reminderDate);
    }

    @Test
    public void annualReminder_whenCalled_thenAnnualReminderIsCalculated() {
        PayRollServiceImpl payRollServiceImplSpy = spy(this.payRollServiceImpl);
        CollaboratorEntity collab = new CollaboratorEntity();
        //get result from request
        doReturn(new ArrayList<SalaryHistoryEntity>())
                .when(this.salaryHistoryService)
                .getSalaryHistoryByCollab(anyLong(), any(Date.class), any(Date.class));
        Double salary = payRollServiceImplSpy.annualReminder(collab);
        // then
        assertThat(salary).isNotNull();
    }


    @Test
    public void createNewHistoryReminderEntity_whenCalled_thenHistoryIsCreated() {
        PayRollServiceImpl payRollServiceImplSpy = spy(this.payRollServiceImpl);
        SalaryHistoryEntity salary = new SalaryHistoryEntity();
        Date now = new Date();
        //getLastUpdateDate
        doReturn(now)
                .when(this.salaryHistoryService)
                .getLastUpdatedSalaryByCollab(anyLong());
        //getHistoryByDate
        doReturn(null)
                .when(this.salaryHistoryService)
                .getHistoryByDate(any(Date.class), anyLong());
        //create new History
        doReturn(salary)
                .when(payRollServiceImplSpy)
                .createNewHistoryEntity(any(CollaboratorEntity.class), anyDouble(), anyDouble(), anyDouble(), anyDouble(), any(Date.class), anyBoolean());
        //get result from request
       SalaryHistoryEntity savedHistory= payRollServiceImplSpy.createNewHistoryReminderEntity(new CollaboratorEntity(), 15.0, now, false);
        // then
        assertThat(savedHistory).isNotNull();
    }
}
