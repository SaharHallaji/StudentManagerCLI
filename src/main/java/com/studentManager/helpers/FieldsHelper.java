package com.studentManager.helpers;

import com.studentManager.exceptions.ValidationException;
import com.studentManager.models.CourseModel;
import com.studentManager.models.ProfessorModel;
import com.studentManager.validators.EntityValidators;

import java.util.*;
import java.util.function.Consumer;

public class FieldsHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getStringInput(String prompt, Consumer<String> validator) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                validator.accept(input);
                return input;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static int getIntInput(String prompt, Consumer<Integer> validator) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = Integer.parseInt(scanner.nextLine().trim());
                validator.accept(input);
                return input;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static Map<String, String> getStudentFields() {
        Map<String, String> fields = new HashMap<>();

        fields.put("firstName", getStringInput("First Name: ",
                input -> EntityValidators.validateName(input, "First name")));

        fields.put("lastName", getStringInput("Last Name: ",
                input -> EntityValidators.validateName(input, "Last name")));

        fields.put("email", getStringInput("Email (e.g., user@example.com): ",
                EntityValidators::validateEmail));

        fields.put("phoneNumber", getStringInput("Phone (e.g., 09123456789): ",
                EntityValidators::validatePhoneNumber));

        fields.put("nationalCode", getStringInput("National Code (10 digits): ",
                EntityValidators::validateNationalCode));

        fields.put("degree", getStringInput("Degree (BACHELOR/MASTER/PHD): ",
                EntityValidators::validateEducationLevel));

        return fields;
    }

    public static Map<String, String> getProfessorFields() {
        Map<String, String> fields = new HashMap<>();

        fields.put("firstName", getStringInput("First Name: ",
                input -> EntityValidators.validateName(input, "First name")));

        fields.put("lastName", getStringInput("Last Name: ",
                input -> EntityValidators.validateName(input, "Last name")));

        fields.put("email", getStringInput("Email: ",
                EntityValidators::validateEmail));

        fields.put("level", getStringInput("Level (INSTRUCTOR/ASSISTANT_PROFESSOR/...): ",
                EntityValidators::validateProfessorLevel));

        fields.put("education", getStringInput("Education (BACHELOR/MASTER/PHD/...): ",
                EntityValidators::validateEducationLevel));

        return fields;
    }

    public static Map<String, String> getCourseFields() {
        Map<String, String> fields = new HashMap<>();

        fields.put("title", getStringInput("Course Title: ",
                input -> EntityValidators.validateTexts(input, "Course Title")));

        fields.put("capacity", String.valueOf(getIntInput("Capacity: ",
                EntityValidators::validateCapacity)));

        fields.put("place", getStringInput("Location/Room: ",
                input -> EntityValidators.validateTexts(input, "Location")));

        fields.put("description", getStringInput("Description: ",
                input -> EntityValidators.validateTexts(input, "Description")));

        fields.put("startTime", getStringInput(
                "Start Time (yyyy-MM-dd HH:mm): ",
                EntityValidators::validateDateTimeFormat
        ));

        fields.put("endTime", getStringInput(
                "End Time (yyyy-MM-dd HH:mm): ",
                EntityValidators::validateDateTimeFormat
        ));

        return fields;
    }

    public static Map<String, String> getDepartmentFields() {
        Map<String, String> fields = new HashMap<>();
        fields.put("name", getStringInput("Department Name: ",
                input -> EntityValidators.validateName(input, "Department name")));
        return fields;
    }

    public static int getIdInput(String entityName) {
        return getIntInput(
                String.format("Enter %s ID: ", entityName),
                id -> {
                    if (id <= 0) {
                        throw new ValidationException("ID must be a positive number");
                    }
                }
        );
    }

    public static int getCourseNumber(Set<CourseModel> items) {
        StringBuilder courses = new StringBuilder();
        for (CourseModel courseModel : items) {
            courses.append(courseModel.getCourseId()).append(" : ").append(courseModel.getTitle()).append("\n");
        }
        List<Integer> validIds = items.stream()
                .map(CourseModel::getCourseId)
                .toList();

        return getIntInput(
                "Choose an Option : \n" + courses,
                item -> {
                    if (!validIds.contains(item)) {
                        throw new ValidationException("ID must be one the above options");
                    }
                }
        );
    }

    public static int getProfessorNumber(List<ProfessorModel> items) {
        StringBuilder professors = new StringBuilder();
        for (ProfessorModel professorModel : items) {
            professors.append(professorModel.getProfessorId())
                    .append(" : ")
                    .append(professorModel.getFirstName())
                    .append(" ")
                    .append(professorModel.getLastName())
                    .append("\n");
        }
        List<Integer> validIds = items.stream()
                .map(ProfessorModel::getProfessorId)
                .toList();
        //TODO handle if there is no option yet.
        return getIntInput(
                "Choose an Option : \n" + professors,
                item -> {
                    if (!validIds.contains(item)) {
                        throw new ValidationException("ID must be one the above options");
                    }
                }
        );
    }
}