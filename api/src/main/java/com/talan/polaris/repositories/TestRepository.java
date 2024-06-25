package com.talan.polaris.repositories;


import com.talan.polaris.entities.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<TestEntity,Long> {
  //  List<TesteEntity> findByPackAndDoneAndTotalScoreBetween(Pack pack, Boolean done, Double value1, Double value2);
   // List<Test> findByPackAndDone(Pack pack,Boolean done);
    List<TestEntity> findBySendAndDone(Boolean send, Boolean done);

  //  List<Test> findByPackAndDoneAndPassageDurationBetween(Pack pack,Boolean done,Long duration1,Long duration2);


  /*  @Query(value="SELECT new com.byblos.evaluation.evaluationservice.dtos.MoyenTechnologyDto(tc.technologyName, AVG(st.score))" +
            "            FROM  PrmTechnology tc, Test t ,ScoreParTechnology st\n" +
            "             where t.pack.id=:idPack and  st.test.testid=t.testid and st.prmTechnology.id=tc.id \n" +
            "             GROUP BY(tc.id)")
    List<MoyenTechnologyDto> findMoyenScore(@Param("idPack") Long idPack);*/

   /* @Query(value="SELECT *  FROM  public.test where pack_id=? ",nativeQuery = true)
    List<TesteEntity> getAllTests(Long idPack);*/


   /* @Query(value="SELECT new com.byblos.evaluation.evaluationservice.dtos.DoneTestResultDto(t,sc)" +
            "            FROM Test t ,ScoreParCategory sc \n" +
            "             where t.pack.id=:idPack and  sc.test.testid=t.testid \n" +
            "             GROUP BY(t)")
    List<DoneTestResultDto> finDoneTest(@Param("idPack") Long idPack);*/

   }
