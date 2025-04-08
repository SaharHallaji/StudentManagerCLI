package com.studentManager.services;

import com.studentManager.dao.StudentDaoImp;
import com.studentManager.helpers.MenuHelper;
import com.studentManager.models.StudentModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StudentManagerService {

    static StudentDaoImp studentDaoImp = new StudentDaoImp();

    public static void addNewStudent() {
        Map<String , String> studentData = MenuHelper.addStudentFields();

        StudentModel studentModel = new StudentModel();

        studentModel.setFirstName(studentData.get("firstName"));
        studentModel.setLastName(studentData.get("lastName"));
        studentModel.setSemester(studentData.get("semester"));
        studentModel.setDegree(studentData.get("degree"));

        Optional<StudentModel> savedStudent =  studentDaoImp.saveStudent(studentModel);

        savedStudent.ifPresentOrElse( student -> {
            System.out.println("\nStudent added successfully with this details: \n");
            System.out.println("ID: " + student.getId());
            System.out.println("Name: " + student.getFirstName() + " " + student.getLastName());
            System.out.println("Degree: " + student.getDegree());
            System.out.println("Semester: " + student.getFirstName() + " " + student.getLastName());
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
                System.out.println("ID : " + student.getId());
                System.out.println("Name : " + student.getFirstName() + " " + student.getLastName());
                System.out.println("Semester : " + student.getSemester());
                System.out.println("Degree : " + student.getDegree());
                System.out.println("---------------------------------------------");
            });

            System.out.println("\nEnd of the list.");
        }
    }

    public static void updateStudent() {
        int id = Integer.parseInt(MenuHelper.getStudentId());

        studentDaoImp.getStudent(id).ifPresentOrElse(input -> {
            System.out.println("\nUpdating student with ID: " + id);

            Map<String, String> updatedData = MenuHelper.addStudentFields();

            input.setFirstName(updatedData.get("firstName"));
            input.setLastName(updatedData.get("lastName"));
            input.setSemester(updatedData.get("semester"));
            input.setDegree(updatedData.get("degree"));

            if (studentDaoImp.updateStudent(input).isPresent()) {
                System.out.println("\nStudent updated successfully with ID: " + id);
            } else {
                System.out.println("\nStudent update failed!");
            }
        },
        ()-> System.out.println("\nStudent not found!"));
    }

    public static void removeStudent() {
        int id = Integer.parseInt(MenuHelper.getStudentId());

        studentDaoImp.getStudent(id).ifPresentOrElse(_ -> {
            if (studentDaoImp.deleteStudent(id).isPresent()) {
                System.out.println("\nStudent deleted successfully!");
            } else {
                System.out.println("\nStudent delete failed!");
            }
        },
        ()-> System.out.println("\nStudent not found!"));
    }

}