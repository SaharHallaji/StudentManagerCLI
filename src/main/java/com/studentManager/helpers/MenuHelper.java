package com.studentManager.helpers;
import com.studentManager.validators.StudentValidators;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

public class MenuHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static String showMenu() {
        System.out.println("\nStudent Management System");
        System.out.println("1. Add a new student");
        System.out.println("2. Show students list");
        System.out.println("3. Update a student");
        System.out.println("4. Remove a student");
        System.out.println("0. Exit");

        return getValidInput("Please select an option: ", input -> {
            try {
                int option = Integer.parseInt(input);
                if (option < 0 || option > 4) {
                    throw new IllegalArgumentException();
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Please enter a number between 0 and 4");
            }
        });
    }

    public static Map<String, String> addStudentFields() {
        Map<String, String> fields = new HashMap<>();

        fields.put("first_name", getValidInput("Enter Student First Name: ",
                input -> StudentValidators.validateName(input, "First name")));

        fields.put("last_name", getValidInput("Enter Student Last Name: ",
                input -> StudentValidators.validateName(input, "Last name")));

        fields.put("semester", getValidInput("Enter Student semester (e.g., spring 2024): ",
                StudentValidators::validateSemester).toLowerCase());

        fields.put("degree", getValidInput("Enter Student Degree (bachelor/master/phd): ",
                StudentValidators::validateDegree).toLowerCase());

        return fields;
    }

    public static String getStudentId() {
        return getValidInput("Enter Student ID: ", StudentValidators::validateUuid);
    }


    private static String getValidInput(String prompt, Consumer<String> validator) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                validator.accept(input);
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}