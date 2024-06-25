package com.talan.polaris.repositories;

import com.talan.polaris.entities.FunctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FunctionRepository extends JpaRepository<FunctionEntity, Long> {
    @Query("SELECT function FROM FunctionEntity AS function "
            + "WHERE function.libelle = :libelle")
    public FunctionEntity findFunctionByLibelle(@Param("libelle") String libelle);
}
