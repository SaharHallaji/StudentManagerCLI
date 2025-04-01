package com.studentManager.validators;

import java.time.Year;
import java.util.Arrays;
import java.util.List;
import com.studentManager.exceptions.StudentValidationException;

public class StudentValidators {
    private static final List<String> VALID_SEMESTERS = Arrays.asList("spring", "fall", "summer", "winter");
    private static final List<String> VALID_DEGREES = Arrays.asList("bachelor", "master", "phd");
    private static final int MAX_NAME_LENGTH = 50;

    public static void validateName(String name, String fieldName) {
        if (name == null || name.trim().isEmpty()) {
            throw new StudentValidationException(fieldName + " cannot be empty");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new StudentValidationException(fieldName + " should be less than " + MAX_NAME_LENGTH + " characters");
        }
    }

    public static void validateSemester(String semester) {
        String[] parts = semester.toLowerCase().split(" ");
        if (parts.length != 2) {
            throw new StudentValidationException("Semester should be in 'spring 2024' format");
        }

        if (!VALID_SEMESTERS.contains(parts[0])) {
            throw new StudentValidationException("Semester must be one of: " + String.join(", ", VALID_SEMESTERS));
        }

        try {
            int year = Integer.parseInt(parts[1]);
            if (year > Year.now().getValue()) {
                throw new StudentValidationException("Year cannot be in the future");
            }
        } catch (NumberFormatException e) {
            throw new StudentValidationException("Year must be a valid number");
        }
    }

    public static void validateUuid(String uuid) {
        if (uuid == null || uuid.trim().isEmpty() ||
                !uuid.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")) {
            throw new StudentValidationException("You should enter a valid UUID (format: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx)");
        }
    }

    public static void validateDegree(String degree) {
        if (!VALID_DEGREES.contains(degree.toLowerCase())) {
            throw new StudentValidationException("Degree must be one of: " + String.join(", ", VALID_DEGREES));
        }
    }
}