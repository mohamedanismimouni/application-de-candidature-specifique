package com.talan.polaris.entities;

import com.talan.polaris.enumerations.MentorshipStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * A mapped entity representing the mentorship domain model.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Entity
@Table(
        name = "MENTORSHIPS",
        uniqueConstraints = @UniqueConstraint(
                columnNames = { "MENTOR_ID", "SKILL", "CAREER_POSITION" }
        )
)
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MentorshipEntity{

    private static final long serialVersionUID = 8722962501003613833L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mentorship_entity")
    @SequenceGenerator(name = "seq_mentorship_entity", sequenceName = "seq_mentorship_entity", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @CreatedDate
    @Column(nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "MENTOR_ID")
    private CollaboratorEntity mentor;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "SKILL")
    private SkillEntity skill;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "CAREER_POSITION")
    private CareerPositionEntity careerPosition;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MentorshipStatusEnum status;

    @Column(nullable = true)
    private Integer mentorRating;

    @Column(nullable = true)
    private String mentorFeedback;

    @Column(nullable = true)
    private Integer menteeRating;

    @Column(nullable = true)
    private String menteeFeedback;



    public MentorshipEntity(
            CollaboratorEntity mentor,
            SkillEntity skill,
            CareerPositionEntity careerPosition,
            MentorshipStatusEnum status) {

        super();
        this.mentor = mentor;
        this.skill = skill;
        this.careerPosition = careerPosition;
        this.status = status;

    }


    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("MentorshipEntity").append("\n")
                .append(super.toString()).append("\n")
                .append("mentor            = ").append(this.getMentor()).append("\n")
                .append("skill             = ").append(this.getSkill()).append("\n")
                .append("careerPosition    = ").append(this.getCareerPosition()).append("\n")
                .append("status            = ").append(this.getStatus()).append("\n")
                .append("mentorRating      = ").append(this.getMentorRating()).append("\n")
                .append("mentorFeedback    = ").append(this.getMentorFeedback()).append("\n")
                .append("menteeRating      = ").append(this.getMenteeRating()).append("\n")
                .append("menteeFeedback    = ").append(this.getMenteeFeedback())
                .toString();

    }

}
