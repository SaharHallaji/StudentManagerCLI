package com.studentManager.models;

import com.studentManager.utils.EducationEnum;
import com.studentManager.utils.LevelEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "professors",catalog = "student_db")
public class ProfessorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "professor_id")
    private int professorId;

    @NotNull()
    @Column(name="first_name")
    private String firstName;

    @NotNull()
    @Column(name = "last_name")
    private String lastName;

    @NotNull()
    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private LevelEnum level;

    @Enumerated(EnumType.STRING)
    private EducationEnum education;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentModel department;


    @CreationTimestamp()
    @Column(name = "created_on")
    private Instant createdOn;

    @UpdateTimestamp()
    @Column(name = "updated_on")
    private Instant updatedOn;

}

