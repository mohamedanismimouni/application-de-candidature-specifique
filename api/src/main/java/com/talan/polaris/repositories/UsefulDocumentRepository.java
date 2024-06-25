package com.talan.polaris.repositories;

import com.talan.polaris.entities.UsefulDocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsefulDocumentRepository extends JpaRepository<UsefulDocumentEntity, Long> {
}
