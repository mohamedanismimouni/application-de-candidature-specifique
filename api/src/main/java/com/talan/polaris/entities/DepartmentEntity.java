package com.talan.polaris.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Department")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"offer"})
public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_department_entity")
    @SequenceGenerator(name = "seq_department_entity", sequenceName = "seq_department_entity", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String departmentName;

    @OneToMany(mappedBy = "department")
    @JsonProperty("offer")
    private Collection<OfferEntity> offerEntities ;


}
