package com.studentManager;

import com.studentManager.helpers.MenuHelper;
import com.studentManager.services.StudentManagerService;

public class Main {
    public static void main(String[] args) {

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
        StudentManagerService.addNewStudent();
    }

    private static void listStudents() {
        StudentManagerService.getAllStudents();
    }

    private static void updateStudent() {
        StudentManagerService.updateStudent();
    }

    private static void removeStudent() {
        StudentManagerService.removeStudent();
    }

}