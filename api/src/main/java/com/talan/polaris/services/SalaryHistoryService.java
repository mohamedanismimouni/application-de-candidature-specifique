package com.talan.polaris.services;
import com.talan.polaris.entities.SalaryHistoryEntity;

import java.util.Collection;
import java.util.Date;
/**
 *
 * definitions related to {@link com.talan.polaris.entities.SalaryHistoryEntity}.
 *
 * @author Imen Mechergui
 * @since 1.0.0
 */
public interface SalaryHistoryService {

    public SalaryHistoryEntity saveSalaryHistory(SalaryHistoryEntity salaryHistory);
    public SalaryHistoryEntity getSalaryHistoryByMonth(Long collabID, Date date);
    public Collection<SalaryHistoryEntity> getSalaryHistoryByCollab(Long collabID, Date startDate, Date endDate);
    public Date getLastUpdatedSalaryByCollab(Long collabID) ;
    public SalaryHistoryEntity getHistoryByDate(Date date ,Long collabID);

}
