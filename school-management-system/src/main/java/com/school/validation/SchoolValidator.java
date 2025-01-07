package com.school.validation;

// validation/SchoolValidator.java
import org.springframework.stereotype.Component;

@Component
public class SchoolValidator {

    public boolean isValidClassName(String className) {
        return className.matches("C[0-9]+");
    }

    public boolean isValidDateOfBirth(String dateOfBirth) {
        return dateOfBirth.matches("\\d{2}-\\d{2}-\\d{4}");
    }
}

