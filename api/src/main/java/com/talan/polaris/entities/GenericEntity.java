package com.talan.polaris.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * A base class for all entities, that contains common properties such as the id 
 * and audit information.
 * <p>
 * It is not mapped to a separate table in the database.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class GenericEntity implements Serializable {

    private static final long serialVersionUID = 8620086810705186778L;

    @Id
    private String id;
    
    @CreatedDate
    @Column(nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    public GenericEntity() {
        super();
    }

    public GenericEntity(String id, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty(access = Access.READ_ONLY)
    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty(access = Access.READ_ONLY)
    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("id                = ").append(this.getId()).append("\n")
                .append("createdAt         = ").append(this.getCreatedAt()).append("\n")
                .append("updatedAt         = ").append(this.getUpdatedAt())
                .toString();

    }

}
