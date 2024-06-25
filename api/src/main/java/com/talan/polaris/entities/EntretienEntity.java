package com.talan.polaris.entities;

import com.talan.polaris.enumerations.EntretienTypeEnum;
import com.talan.polaris.enumerations.ResultEntretienEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntretienEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_entretien")
    private EntretienTypeEnum entretienType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    private CandidateEntity candidate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "collaborator_id", referencedColumnName = "id")
    private CollaboratorEntity collaborator;

    @Column(name = "comment")
    private String comment;

    @Column(name = "result")
    private ResultEntretienEnum result;

    @Column(name = "send")
    private boolean send ;

    @Column(name = "done")
    private  boolean done;

    @Column(name="date")
    private Date date;
}
