package com.studentManager.validators;

import com.studentManager.exceptions.ValidationException;
import com.studentManager.utils.EducationEnum;
import com.studentManager.utils.LevelEnum;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class EntityValidators {
    private static final int MAX_NAME_LENGTH = 225;
    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void validateDateTimeFormat(String input) {
        try {
            LocalDateTime.parse(input, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm.");
        }
    }

    public static void validateName(String name, String fieldName) {
        validateTexts(name, fieldName);
        if (!name.matches("^[a-zA-Z\\s]+$")) {
            throw new ValidationException(fieldName + " must contain only letters");
        }
    }

    public static void validateTexts(String text, String fieldName) {
        if (text == null || text.trim().isEmpty()) {
            throw new ValidationException(fieldName + " cannot be empty");
        }
        if (text.length() > MAX_NAME_LENGTH) {
            throw new ValidationException(fieldName + " exceeds " + MAX_NAME_LENGTH + " chars");
        }
    }

    public static void validateEmail(String email) {
        if (email == null || !EMAIL_REGEX.matcher(email).matches()) {
            throw new ValidationException("Invalid email format");
        }
    }

    public static void validatePhoneNumber(String phone) {
        if (phone == null) {
            throw new ValidationException("Phone number cannot be null");
        }

        if (!phone.matches("^\\+?\\d+$")) {
            throw new ValidationException("Phone number can only contain digits or '+' prefix");
        }

        String digitsOnly = phone.replace("+", "");
        if (!digitsOnly.matches("^(98|0)?9\\d{9}$")) {
            throw new ValidationException("Invalid Iranian phone number. Valid formats: 0912.../912.../+98912...");
        }
    }

    public static void validateNationalCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new ValidationException("National code cannot be empty");
        }

        if (!code.matches("^\\d{10}$")) {
            throw new ValidationException("National code must be 10 digits");
        }

        if (code.matches("^(\\d)\\1{9}$")) {
            throw new ValidationException("Invalid national code (all digits are the same)");
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(code.charAt(i)) * (10 - i);
        }

        int remainder = sum % 11;
        int controlDigit = Character.getNumericValue(code.charAt(9));

        boolean isValid = (remainder < 2 && controlDigit == remainder) ||
                (remainder >= 2 && controlDigit == (11 - remainder));

        if (!isValid) {
            throw new ValidationException("Invalid Iranian national code");
        }
    }

    public static void validateEducationLevel(String level) {
        try {
            EducationEnum.valueOf(level.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid degree. Use: BACHELOR, MASTER, or PHD");
        }
    }

    public static void validateProfessorLevel(String level) {
        try {
            LevelEnum.valueOf(level.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException(
                    "Invalid degree. Use: INSTRUCTOR, ASSISTANT_PROFESSOR, ASSOCIATE_PROFESSOR, PROFESSOR"
            );
        }
    }


    public static void validateCapacity(int capacity) {
        if (capacity <= 0) {
            throw new ValidationException("Capacity must be positive");
        }
    }

}