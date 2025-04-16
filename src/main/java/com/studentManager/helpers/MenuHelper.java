package com.studentManager.helpers;

import java.util.*;

import static com.studentManager.helpers.FieldsHelper.getIntInput;
import static com.studentManager.services.StudentService.*;
import static com.studentManager.services.DepartmentService.*;
import static com.studentManager.services.CourseService.*;
import static com.studentManager.services.ProfessorService.*;

public class MenuHelper {

    public static int showFirstMenu() {
        System.out.println("\nStudent Management System");
        System.out.println("1. Student CRUD.");
        System.out.println("2. Course CRUD.");
        System.out.println("3. Professor CRUD.");
        System.out.println("4. Department CRUD.");
        System.out.println("0. Exit");

        return getIntInput("Please select an option: ", input -> {
            try {
                int option = input;
                if (option < 0 || option > 4) {
                    throw new IllegalArgumentException();
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Please enter a number between 0 and 4");
            }
        });
    }


    private static final Map<String, List<String>> MENU_OPTIONS = new HashMap<>() {{
        put("student", Arrays.asList(
                "Add a student",
                "Show student list",
                "Update a student",
                "Remove a student",
                "Register course",
                "Withdraw from course"
        ));
        put("course", Arrays.asList(
                "Add a course",
                "Show course list",
                "Update a course",
                "Remove a course",
                "Assign professor",
                "Remove professor from course"
        ));
        put("professor", Arrays.asList(
                "Add a professor",
                "Show professor list",
                "Update a professor",
                "Remove a professor"
        ));
        put("department", Arrays.asList(
                "Add a department",
                "Show department list",
                "Update a department",
                "Remove a department",
                "Add professor to department",
                "Remove professor from department"
        ));
    }};

    public static int showSecondMenu(String schema) {
        List<String> options = MENU_OPTIONS.get(schema.toLowerCase());

        if (options == null) {
            throw new IllegalArgumentException("No menu options defined for schema: " + schema);
        }

        System.out.println("\n" + capitalize(schema) + " Menu:");
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        System.out.println("0. Exit");

        int maxOption = options.size();

        return getIntInput("Please select an option: ", input -> {
            if (input < 0 || input > maxOption) {
                throw new IllegalArgumentException("Please enter a number between 0 and " + maxOption);
            }
        });
    }

    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }


    public static void handleStudentMenu() {
        int chosenOption = MenuHelper.showSecondMenu("student");

        switch (chosenOption) {
            case 1 -> addNewStudent();
            case 2 -> getAllStudents();
            case 3 -> updateStudent();
            case 4 -> removeStudent();
            case 5 -> registerCourse();
            case 6 -> withdrawalCourse();
            case 0 -> System.exit(0);
            default -> System.out.println("Invalid option!");
        }
    }

    public static void handleCourseMenu() {
        int chosenOption = MenuHelper.showSecondMenu("course");

        switch (chosenOption) {
            case 1 -> addNewCourse();
            case 2 -> getAllCourses();
            case 3 -> updateCourse();
            case 4 -> removeCourse();
            case 5 -> defineProfessor();
            case 6 -> removeProfessorFromCourse();
            case 0 -> System.exit(0);
            default -> System.out.println("Invalid option!");
        }
    }

    public static void handleProfessorMenu() {
        int chosenOption = MenuHelper.showSecondMenu("professor");

        switch (chosenOption) {
            case 1 -> addNewProfessor();
            case 2 -> getAllProfessors();
            case 3 -> updateProfessor();
            case 4 -> removeProfessor();
            case 0 -> System.exit(0);
            default -> System.out.println("Invalid option!");
        }
    }

    public static void handleDepartmentMenu() {
        int chosenOption = MenuHelper.showSecondMenu("department");

        switch (chosenOption) {
            case 1 -> addNewDepartment();
            case 2 -> getAllDepartments();
            case 3 -> updateDepartment();
            case 4 -> removeDepartment();
            case 5 -> addProfessorToDepartment();
            case 6 -> removeProfessorFromDepartment();
            case 0 -> System.exit(0);
            default -> System.out.println("Invalid option!");
        }
    }
}