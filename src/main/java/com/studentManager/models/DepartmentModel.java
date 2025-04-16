package com.studentManager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Set;


@Setter
@Getter
@Entity
@Table(name = "department",catalog = "student_db")
public class DepartmentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "department_id")
    private int departmentId;

    @NotNull()
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "department")
    private Set<ProfessorModel> professors;

    @CreationTimestamp()
    @Column(name = "created_on")
    private Instant createdOn;

    @UpdateTimestamp()
    @Column(name = "updated_on")
    private Instant updatedOn;
}
