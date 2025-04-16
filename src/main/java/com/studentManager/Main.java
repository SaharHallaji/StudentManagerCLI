package com.studentManager;

import com.studentManager.helpers.MenuHelper;

import static com.studentManager.helpers.MenuHelper.*;

public class Main {
    public static void main(String[] args) {

        while (true) {
            try {
                int chosenOption = MenuHelper.showFirstMenu();

                switch (chosenOption) {
                    case 1 -> handleStudentMenu();
                    case 2 -> handleCourseMenu();
                    case 3 -> handleProfessorMenu();
                    case 4 -> handleDepartmentMenu();
                    case 0 -> System.exit(0);
                    default -> System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

}