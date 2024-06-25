package com.talan.polaris.repositories;

import com.talan.polaris.entities.ProfileTypeEntity;
import com.talan.polaris.enumerations.ProfileTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileTypeRepository extends JpaRepository<ProfileTypeEntity, Long> {
    @Query("SELECT profileType FROM ProfileTypeEntity AS profileType WHERE profileType.label LIKE :label")
    public Optional<ProfileTypeEntity> findProfileTypeByLabel(@Param("label")ProfileTypeEnum label);
}
