package com.talan.polaris.repositories;
import com.talan.polaris.entities.SalaryHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.Date;

/**
 *
 * data access operations tied to the {@link SalaryHistoryEntity} domain model.
 * @author Imen Mechergui
 * @since 1.0.0
 */
@Repository
public interface SalaryHistoryRepository extends JpaRepository<SalaryHistoryEntity, Long> {

    @Query("SELECT history FROM SalaryHistoryEntity AS history WHERE history.collaborator.id = :id AND history.payRollDate >= :startDate AND history.payRollDate <= :endDate")
    public Collection<SalaryHistoryEntity> getSalaryHistoryByCollab(@Param("id")Long collabID, @Param("startDate") Date startDate,@Param("endDate") Date endDate);

    @Query("SELECT history FROM SalaryHistoryEntity AS history WHERE history.collaborator.id = :id AND history.payRollDate = :date ")
    public SalaryHistoryEntity getSalaryHistoryByMonth(@Param("id")Long collabID,@Param("date") Date date);

    @Query("SELECT MAX(history.payRollDate) FROM SalaryHistoryEntity AS history WHERE history.collaborator.id = :id ")
    public Date getLastUpdatedSalaryByCollab(@Param("id")Long collabID);


    @Query("SELECT history FROM SalaryHistoryEntity AS history WHERE history.payRollDate = :date AND history.collaborator.id = :id " )
    public SalaryHistoryEntity getHistoryByDate(@Param("date")Date date ,@Param("id")Long collabID);


   /* @Query("SELECT MAX(history.encryptedNetSalary) FROM SalaryHistoryEntity AS history WHERE history.collaborator.id = :id")
    public Double getMaxNetSalary(@Param("id")Long collabID);

    @Query("SELECT MAX(history.encryptedBaseSalary) FROM SalaryHistoryEntity AS history WHERE history.collaborator.id = :id")
    public Double getMaxBaseSalary(@Param("id")Long collabID);
*/
}
