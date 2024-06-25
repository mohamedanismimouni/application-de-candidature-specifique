package com.talan.polaris.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.talan.polaris.entities.ConfigScore;



public interface ConfigScoreRepository  extends JpaRepository<ConfigScore, Long>{
	@Query("select u from ConfigScore u where u.componentLabel =?1 ")
	public ConfigScore findByComponentLabel(String componentLabel);

}
