package com.talan.polaris.entities;

import java.time.Instant;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
 import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


import lombok.Getter;
import lombok.Setter;
 

@Getter
@Setter
@Entity
public class QuizEntity  {
	
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_quiz_entity")
	@SequenceGenerator(name = "seq_quiz_entity",sequenceName = "seq_quiz_entity", allocationSize = 100)
    @Id
    @Column(name = "id")
    private String id;
   
    @CreatedDate
    @Column(nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;
    @Column(nullable = false)
    private String question;
    
    @Column(nullable = false)
    private String response;
    
    @ManyToMany
    @JoinTable(name = "answered_by" )
    Collection<CollaboratorEntity> answeredBy;
    public QuizEntity() {}
    public QuizEntity(
            String id,
            Instant createdAt,
            Instant updatedAt,
            String question,
            String response,Collection<CollaboratorEntity> answeredBy) {

    	 this.id = id;
         this.createdAt = createdAt;
         this.updatedAt = updatedAt;
         this.question = question;
         this.response = response;
         this.answeredBy=answeredBy;
         

    }
    @Override
    public String toString() {
        return new StringBuilder()
        		   .append("id          = ").append(this.getId()).append("\n")
                   .append("createdAt          = ").append(this.getCreatedAt()).append("\n")
                   .append("updatedAt          = ").append(this.getUpdatedAt()).append("\n")
                   .append("question       = ").append(this.getQuestion()).append("\n")
                   .append("response             = ").append(this.getResponse()).append("\n")
                   .append("answeredBy             = ").append(this.getAnsweredBy()).append("\n").toString();
 
    }
    public void addAnsweredBy(CollaboratorEntity collaborator) {
    	this.answeredBy.add(collaborator);
    }
}
