package com.studentManager.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity
@Table(name = "students",catalog = "student_db")
public class StudentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column()
    private String degree;

    @Column()
    private String semester;

    public StudentModel(int id, String firstName, String lastName, String degree, String semester) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.degree = degree;
        this.semester = semester;
    }

    public StudentModel() {}

}
