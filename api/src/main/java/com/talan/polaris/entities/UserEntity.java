/*
package com.talan.polaris.entities;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.talan.polaris.enumerations.AccountStatusEnum;
import com.talan.polaris.enumerations.ProfileTypeEnum;
import com.talan.polaris.utils.validation.constraints.EmailConstraint;
import com.talan.polaris.utils.validation.constraints.PasswordConstraint;
import com.talan.polaris.utils.validation.groups.CreateValidationGroup;
import com.talan.polaris.utils.validation.groups.UpdateValidationGroup;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;

*/
/**
 * UserEntity.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 *//*

@Entity
@Table(name = "USERS")
*/
/*@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DC")
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, 
    include = JsonTypeInfo.As.PROPERTY, 
    property = "profileType", 
    visible = true
)
@JsonSubTypes({
	@Type(
        name = ProfileTypeConstants.DIRECTOR_PROFILE_TYPE, 
        value = DirectorEntity.class
    ),
	@Type(
        name = ProfileTypeConstants.HR_RESPONSIBLE_PROFILE_TYPE, 
        value = HRResponsibleEntity.class
    ),
    @Type(
        name = ProfileTypeConstants.MANAGER_PROFILE_TYPE, 
        value = ManagerEntity.class
    ),
    @Type(
        name = ProfileTypeConstants.COLLABORATOR_PROFILE_TYPE, 
        value = CollaboratorEntity.class
    )
})*//*

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class UserEntity{

    private static final long serialVersionUID = 1348562529618414282L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_entity")
    @SequenceGenerator(name = "seq_user_entity", sequenceName = "seq_user_entity", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @CreatedDate
    @Column(nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    @NotNull(groups = { CreateValidationGroup.class, UpdateValidationGroup.class })
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProfileTypeEnum profileType;

    @NotNull(groups = { CreateValidationGroup.class, UpdateValidationGroup.class })
    @EmailConstraint(groups = { CreateValidationGroup.class, UpdateValidationGroup.class })
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(groups = { UpdateValidationGroup.class })
    @PasswordConstraint(groups = { UpdateValidationGroup.class })
    @Column(nullable = false)
    private String password;

    @NotNull(groups = { UpdateValidationGroup.class })
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatusEnum accountStatus;

    @NotBlank(groups = { CreateValidationGroup.class, UpdateValidationGroup.class })
    @Column(nullable = false)
    private String firstName;

    @NotBlank(groups = { CreateValidationGroup.class, UpdateValidationGroup.class })
    @Column(nullable = false)
    private String lastName;

    @OneToOne()
    @JoinColumn(name = "collab_id", referencedColumnName = "id")
    private CollaboratorEntity collab;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<RoleEntity> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_profile_type",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "profile_type_id", referencedColumnName = "id"))
    private Collection<ProfileTypeEntity> profileTypeEntity;

    @OneToMany(mappedBy="createdBy")
    private Collection<DocumentRequestEntity> documentRequests;

    //collaboratorEntity
    @NotNull(groups = { CreateValidationGroup.class, UpdateValidationGroup.class })
    @Column(nullable = true)
    private LocalDate recruitedAt;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, name = "MEMBER_OF")
    private TeamEntity memberOf;

    @Column(nullable = true)
    private String secretWord;

    @Column(nullable = true)
    private boolean passedOnboardingProcess;

    @OneToMany(mappedBy="collaborator")
    private Collection<DocumentRequestEntity> requests;

    //managerEntity
    @OneToOne(optional = true, fetch = FetchType.LAZY, mappedBy = "managedBy")
    private TeamEntity managerOf;

    public UserEntity() {
    }

    public UserEntity(
            Long id,
            Instant createdAt,
            Instant updatedAt,
            String email,
            String password,
            AccountStatusEnum accountStatus,
            String firstName,
            String lastName,
            Collection<DocumentRequestEntity> documentRequests,
            Collection<ProfileTypeEntity> profileTypeEntity,
            LocalDate recruitedAt,
            TeamEntity memberOf,
            Collection<DocumentRequestEntity> requests,
            TeamEntity managerOf) {

        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.email = email;
        this.password = password;
        this.accountStatus = accountStatus;
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentRequests = documentRequests;
        this.profileTypeEntity = profileTypeEntity;

        this.recruitedAt = recruitedAt;
        this.memberOf = memberOf;
        this.requests = requests;
        this.managerOf = managerOf;

    }
    @JsonProperty(access = Access.WRITE_ONLY)
    public String getPassword() {
        return this.password;
    }


    //collabpratorEntity
    public LocalDate getRecruitedAt() {
        return this.recruitedAt;
    }

    public void setRecruitedAt(LocalDate recruitedAt) {
        this.recruitedAt = recruitedAt;
    }

    public Collection<DocumentRequestEntity> getRequests() {
        return requests;
    }

    public void setRequests(Collection<DocumentRequestEntity> requests) {
        this.requests = requests;
    }

    @JsonIgnore
    public TeamEntity getMemberOf() {
        return this.memberOf;
    }

    @JsonSetter
    public void setMemberOf(TeamEntity memberOf) {
        this.memberOf = memberOf;
    }

    @JsonIgnore
    public String getSecretWord() {
        return this.secretWord;
    }

    @JsonSetter
    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
    }

    @JsonProperty(access = Access.READ_ONLY)
    public boolean getPassedOnboardingProcess() {
        return this.passedOnboardingProcess;
    }

    public void setPassedOnboardingProcess(boolean passedOnboardingProcess) {
        this.passedOnboardingProcess = passedOnboardingProcess;
    }

    //manager
    @JsonIgnore
    public TeamEntity getManagerOf() {
        return this.managerOf;
    }

    public void setManagerOf(TeamEntity managerOf) {
        this.managerOf = managerOf;
    }


    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("UserEntity").append("\n")
                .append("id          = ").append(this.getId()).append("\n")
                .append("createdAt          = ").append(this.getCreatedAt()).append("\n")
                .append("updatedAt          = ").append(this.getUpdatedAt()).append("\n")
                .append("email             = ").append(this.getEmail()).append("\n")
                .append("password          = [ SECRET ]").append("\n")
                .append("accountStatus     = ").append(this.getAccountStatus()).append("\n")
                .append("firstName         = ").append(this.getFirstName()).append("\n")
                .append("lastName          = ").append(this.getLastName()).append("\n")
                .append("requests          = ").append(this.getDocumentRequests()).append("\n")
                .append("profileTypeEntity = ").append(this.getProfileTypeEntity()).append("\n")

                .append("recruitedAt       = ").append(this.getRecruitedAt()).append("\n")
                .append("secretWord        = ").append(this.getSecretWord()).append("\n")
                .append("onboardingProcess = ").append(this.getPassedOnboardingProcess()).append("\n")
                .append("requests           = ").append(this.getRequests()).append("\n")
                .append("managerOf          = ").append(this.getMemberOf())
                .toString();

    }

}
*/
