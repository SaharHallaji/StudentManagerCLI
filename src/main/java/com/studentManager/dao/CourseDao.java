package com.studentManager.dao;

import com.studentManager.models.CourseModel;

import java.util.List;
import java.util.Optional;

public interface CourseDao {

    List<CourseModel> getAllCourses();
    Optional<CourseModel> updateCourse(CourseModel course);
    Optional<CourseModel> saveCourse(CourseModel course);
    Optional<CourseModel> deleteCourse(int courseId);
    Optional<CourseModel> getCourse(int courseId);

    Optional<CourseModel> defineProfessor(int courseId, int professorId);
    Optional<CourseModel> removeProfessor(int courseId);
}
