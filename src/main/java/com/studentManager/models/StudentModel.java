package com.studentManager.models;

import com.studentManager.utils.EducationEnum;
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
@Table(name = "students",catalog = "student_db")
public class StudentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "student_id")
    private int studentId;

    @NotNull()
    @Column(name="first_name")
    private String firstName;

    @NotNull()
    @Column(name = "last_name")
    private String lastName;

    @NotNull()
    @Column()
    @Enumerated(EnumType.STRING)
    private EducationEnum degree;

    @NotNull()
    @Column(unique = true)
    private String email;

    @NotNull()
    @Column(unique = true, name = "phone_number")
    private String phoneNumber;

    @NotNull()
    @Column(unique = true, name = "national_code")
    private String nationalCode;


    @CreationTimestamp()
    @Column(name = "created_on")
    private Instant createdOn;


    @UpdateTimestamp()
    @Column(name = "updated_on")
    private Instant updatedOn;

    //should I use : cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH} ?
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<CourseModel> courses;


    public StudentModel() {}

}
