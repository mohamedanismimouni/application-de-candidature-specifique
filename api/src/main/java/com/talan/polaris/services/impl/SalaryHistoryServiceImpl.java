package com.talan.polaris.services.impl;
import com.talan.polaris.entities.SalaryHistoryEntity;
import com.talan.polaris.repositories.SalaryHistoryRepository;
import com.talan.polaris.services.SalaryHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Date;
@Service
public class SalaryHistoryServiceImpl implements SalaryHistoryService {
    public final SalaryHistoryRepository salaryHistoryRepository;

    @Autowired
    public SalaryHistoryServiceImpl(SalaryHistoryRepository salaryHistoryRepository) {
        this.salaryHistoryRepository=salaryHistoryRepository;
    }

    @Override
    public SalaryHistoryEntity saveSalaryHistory(SalaryHistoryEntity salaryHistory) {
        return this.salaryHistoryRepository.save(salaryHistory);
    }

    @Override
    public SalaryHistoryEntity getSalaryHistoryByMonth(Long collabID, Date date) {
        return this.salaryHistoryRepository.getSalaryHistoryByMonth(collabID,date);
    }

    @Override
    public Collection<SalaryHistoryEntity> getSalaryHistoryByCollab(Long collabID,Date startDate, Date endDate) {
        return this.salaryHistoryRepository. getSalaryHistoryByCollab(collabID,startDate,endDate);
    }

    @Override
    public Date getLastUpdatedSalaryByCollab(Long collabID) {
        return this.salaryHistoryRepository.getLastUpdatedSalaryByCollab(collabID);
    }

    @Override
    public SalaryHistoryEntity getHistoryByDate(Date date ,Long collabID) {
        return this.salaryHistoryRepository.getHistoryByDate(date ,collabID);
    }
}
