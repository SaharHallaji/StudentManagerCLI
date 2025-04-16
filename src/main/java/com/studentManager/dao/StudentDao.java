package com.studentManager.dao;

import com.studentManager.models.StudentModel;

import java.util.List;
import java.util.Optional;

public interface StudentDao {

    List<StudentModel> getAllStudent();
    Optional<StudentModel> updateStudent(StudentModel Student);
    Optional<StudentModel> saveStudent(StudentModel student);
    Optional<StudentModel> deleteStudent(int studentId);
    Optional<StudentModel> getStudent(int studentId);

    Optional<StudentModel> registerCourse(int studentId, int courseId);
    Optional<StudentModel> removeCourse(int studentId, int courseId);
}
