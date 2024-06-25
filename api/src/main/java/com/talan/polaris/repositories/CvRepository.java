package com.talan.polaris.repositories;

import com.talan.polaris.entities.CvEntity;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CvRepository extends ElasticsearchRepository<CvEntity, String> {
    @Query("{\"match\": {\"attachment.content\": \"?0\"}}")
    List<CvEntity> search(String keyword);
    Optional<CvEntity> findById(String id);
}
