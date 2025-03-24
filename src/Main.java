import helpers.MenuHelper;
import repositories.StudentRepository;

import java.util.Map;

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
        Map<String, String> studentData = MenuHelper.addStudentFields();
        String id = StudentRepository.INSTANCE.addStudent(studentData);
        System.out.println("\nStudent added successfully with ID: " + id);
    }

    private static void listStudents() {
        System.out.println("\nList of students: ");
        StudentRepository.INSTANCE.getAllStudents().forEach(student -> {
            System.out.println("ID : " + student.get("id"));
            System.out.println("Name : " + student.get("firstName") + " " + student.get("lastName"));
            System.out.println("Semester : " + student.get("semester"));
            System.out.println("Degree : " + student.get("degree"));
            System.out.println("---------------------------------------------");
        });

        System.out.println("\nEnd of the list.");
    }

    private static void updateStudent() {
        //TODO implement it.
    }

    private static void removeStudent() {
        //TODO implement it.
    }

}