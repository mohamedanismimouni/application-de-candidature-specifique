package com.talan.polaris.entities;


import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;


/**
 * HumeurCollabEntity.
 * SalaryHistoryEntity.
 *
 * @author Nadhir Fallah
 * @since 1.0.0
 */

@Entity
@Table(name = "Mood_collab")
@Getter
@Setter
public class MoodCollab {
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  @Column(name = "id_mood_collab")
	  private Long idMoodCollab;
	 
	  
	  @Column(name = "id_mood",nullable = false)
	  private Long idMood;
	  
	  @Column(nullable = true)
	  @Temporal(TemporalType.DATE)
	  private Date date ; 

	 
	  @JoinColumn(name = "id_collab")
	  private Long idCollab;
}
