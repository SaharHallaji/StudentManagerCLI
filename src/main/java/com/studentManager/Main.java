package com.studentManager;

import com.studentManager.config.DatabaseConfig;
import com.studentManager.helpers.MenuHelper;
import com.studentManager.repositories.StudentRepository;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        DatabaseConfig.connectToDB();
        Runtime.getRuntime().addShutdownHook(new Thread(DatabaseConfig::disconnectDB));

        while (true) {
            try {
                int chosenOption = Integer.parseInt(MenuHelper.showMenu());

                switch (chosenOption) {
                    case 1 -> addStudent();
                    case 2 -> listStudents();
                    case 3 -> updateStudent();
                    case 4 -> removeStudent();
                    case 0 -> System.exit(0);
                    default -> System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addStudent() {
        Map<String, String> studentData = MenuHelper.addStudentFields();
        String id = StudentRepository.getInstance().addStudent(studentData);
        System.out.println("\nStudent added successfully with ID: " + id);
    }

    private static void listStudents() {
        List<Map<String, String>> students = StudentRepository.getInstance().getAllStudents();
        if (students.isEmpty()) {
            System.out.println("\nNo students found!");
        } else {
            System.out.println("\nList of students: ");
            students.forEach(student -> {
                System.out.println("ID : " + student.get("id"));
                //TODO fix it.
                System.out.println("Name : " + student.get("first_name") + " " + student.get("last_name"));
                System.out.println("Semester : " + student.get("semester"));
                System.out.println("Degree : " + student.get("degree"));
                System.out.println("---------------------------------------------");
            });

            System.out.println("\nEnd of the list.");
        }
    }

    private static void updateStudent() {
        String id = MenuHelper.getStudentId();

        if (StudentRepository.getInstance().studentExists(id)) {
            System.out.println("\nUpdating student with ID: " + id);
            Map<String, String> updatedData = MenuHelper.addStudentFields();

            if (StudentRepository.getInstance().updateStudent(id, updatedData)) {
                System.out.println("\nStudent updated successfully with ID: " + id);
            } else {
                System.out.println("\nStudent update failed!");
            }
        } else {
            System.out.println("\nStudent not found!");
        }
    }

    private static void removeStudent() {
        String id = MenuHelper.getStudentId();

        if (StudentRepository.getInstance().studentExists(id)) {
            if (StudentRepository.getInstance().deleteStudent(id)) {
                System.out.println("\nStudent deleted successfully!");
            } else {
                System.out.println("\nStudent delete failed!");
            }
        } else {
            System.out.println("\nStudent not found!");
        }
    }

}