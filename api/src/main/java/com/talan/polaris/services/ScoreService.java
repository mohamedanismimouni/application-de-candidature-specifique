package com.talan.polaris.services;
public interface ScoreService {
	 public Integer increment(Long id , String componentLabel);
	 public Integer getScore(String componentLabel);
	 public Integer getScoreByUserEmail(String email);
	 public Integer decrement(Long id , String componentLabel);
}
