package com.studentManager.services;

import com.studentManager.dao.CourseDaoImp;
import com.studentManager.dao.StudentDaoImp;
import com.studentManager.helpers.FieldsHelper;
import com.studentManager.models.CourseModel;
import com.studentManager.models.StudentModel;
import com.studentManager.utils.EducationEnum;

import java.util.*;

public class StudentService {

    static StudentDaoImp studentDaoImp = new StudentDaoImp();
    static CourseDaoImp courseDaoImp = new CourseDaoImp();

    public static void addNewStudent() {
       Map<String , String> studentData = FieldsHelper.getStudentFields();

        StudentModel studentModel = new StudentModel();

        studentModel.setFirstName(studentData.get("firstName"));
        studentModel.setLastName(studentData.get("lastName"));
        studentModel.setEmail(studentData.get("email"));
        studentModel.setNationalCode(studentData.get("nationalCode"));
        studentModel.setDegree(EducationEnum.valueOf(studentData.get("degree")));
        studentModel.setPhoneNumber(studentData.get("phoneNumber"));

        Optional<StudentModel> savedStudent =  studentDaoImp.saveStudent(studentModel);

        savedStudent.ifPresentOrElse( student -> {
            System.out.println("\nStudent added successfully with id: \n");
            System.out.println("ID: " + student.getStudentId());
        },
        ()-> System.out.println("Something went wrong while saving the student."));
    }

    public static void getAllStudents() {
        List<StudentModel> students = studentDaoImp.getAllStudent();

        if (students.isEmpty()) {
            System.out.println("\nNo students found!");
        } else {
            System.out.println("\nList of students: ");

            students.forEach(student -> {
                System.out.println("ID : " + student.getStudentId());
                System.out.println("Name : " + student.getFirstName() + " " + student.getLastName());
                System.out.println("Degree : " + student.getDegree());
                System.out.println("Email : " + student.getEmail());
                System.out.println("Phone Number : " + student.getPhoneNumber());
                System.out.println("National Code : " + student.getNationalCode());
                if (student.getCourses() != null && !student.getCourses().isEmpty()) {
                    System.out.println("Courses : ");
                    student.getCourses().forEach(course -> System.out.println(
                            " + " +
                            course.getCourseId() +
                            " " +
                            course.getTitle())
                    );
                } else {
                    System.out.println("Student has no courses! ");
                }
                System.out.println("---------------------------------------------");
            });

            System.out.println("\nEnd of the list.");
        }
    }

    public static void updateStudent() {
        int id = FieldsHelper.getIdInput("Student");

        studentDaoImp.getStudent(id).ifPresentOrElse(input -> {
            System.out.println("\nUpdating student with ID: " + id);

            Map<String, String> updatedData = FieldsHelper.getStudentFields();

            input.setFirstName(updatedData.get("firstName"));
            input.setLastName(updatedData.get("lastName"));
            input.setEmail(updatedData.get("email"));
            input.setNationalCode(updatedData.get("nationalCode"));
            input.setDegree(EducationEnum.valueOf(updatedData.get("degree")));
            input.setPhoneNumber(updatedData.get("phoneNumber"));

            if (studentDaoImp.updateStudent(input).isPresent()) {
                System.out.println("\nStudent updated successfully with ID: " + id);
            } else {
                System.out.println("\nStudent update failed!");
            }
        },
        ()-> System.out.println("\nStudent not found!"));
    }

    public static void removeStudent() {
        int id = FieldsHelper.getIdInput("Student");
        studentDaoImp.getStudent(id).ifPresentOrElse(student -> {
            student.getCourses().forEach(course ->
                    studentDaoImp.removeCourse(
                            student.getStudentId(),
                            course.getCourseId()
                    ).ifPresentOrElse(_ -> System.out.print("✔"),
                    ()-> System.out.println("Something went while removing course : " + course.getCourseId())));

            if ( studentDaoImp.deleteStudent(id).isPresent()) {
                System.out.println("\nStudent deleted successfully!");
            } else {
                System.out.println("\nStudent delete failed!");
            }
        },
        ()-> System.out.println("\nStudent not found!"));
    }

    public static void registerCourse() {
        int studentId = FieldsHelper.getIdInput("Student");

        studentDaoImp.getStudent(studentId).ifPresentOrElse(_ ->{
            List<CourseModel> coursesList = courseDaoImp.getAllCourses();

            Set<CourseModel> courses = new HashSet<>(coursesList);

            int courseId = FieldsHelper.getCourseNumber(courses);

            studentDaoImp.registerCourse(studentId, courseId)
                    .ifPresentOrElse(_ -> System.out.println("\nCourse registered successfully!"),
                    ()-> System.out.println("\nSomething went wrong while registering the course.!"));
        }, ()-> System.out.println("\nStudent not found!"));
    }

    public static void withdrawalCourse() {
        int studentId = FieldsHelper.getIdInput("Student");

        studentDaoImp.getStudent(studentId).ifPresentOrElse(course ->{
            Set<CourseModel> courses = course.getCourses();
            int courseId = FieldsHelper.getCourseNumber(courses);

            studentDaoImp.removeCourse(studentId, courseId).ifPresentOrElse(_ ->
                            System.out.println("\nCourse withdrawal was successful.!"),
                    ()-> System.out.println("\nSomething went wrong while processing the withdrawal of the course.!"));

        }, ()-> System.out.println("\nStudent not found!"));

    }

}