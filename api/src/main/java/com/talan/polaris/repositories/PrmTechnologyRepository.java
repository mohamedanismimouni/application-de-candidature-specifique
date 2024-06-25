package com.talan.polaris.repositories;


import com.talan.polaris.entities.PrmTechnologyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrmTechnologyRepository extends JpaRepository<PrmTechnologyEntity, Long> {
    public void deleteById(Long id );
    PrmTechnologyEntity findPrmTechnologyByTechnologyName(String technologyName);

}
