package com.xyz.schoolManagementSystem.util;
import java.util.regex.Pattern;

public class ValidationUtil {

    // Regular expression for validating email addresses
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    // Method to validate email format
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    // Method to validate if a string is not null or empty
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    // Method to validate if a number is positive
    public static boolean isPositive(int number) {
        return number > 0;
    }

    // Method to validate date format (example: YYYY-MM-DD)
    public static boolean isValidDateFormat(String date) {
        // Simple regex for date format YYYY-MM-DD
        String dateRegex = "^\\d{4}-\\d{2}-\\d{2}$";
        return date != null && date.matches(dateRegex);
    }

    // Additional validation methods can be added here
}