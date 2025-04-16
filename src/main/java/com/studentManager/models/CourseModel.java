package com.studentManager.models;

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
@Table(name = "courses",catalog = "student_db")
public class CourseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "course_id")
    private int courseId;

    @Column(unique = true)
    @NotNull()
    private String title;

    private String description;

    @NotNull()
    private int capacity;

    @NotNull()
    private String place;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professor_id")
    private ProfessorModel professor;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @CreationTimestamp()
    @Column(name = "created_on")
    private Instant createdOn;

    @UpdateTimestamp()
    @Column(name = "updated_on")
    private Instant updatedOn;
}
