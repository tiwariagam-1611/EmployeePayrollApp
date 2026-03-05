package com.payrollapp.validation;

import java.util.regex.Pattern;

// ============== Validation Service ================
public class ValidationService {

    // Sanitizes input before validation
    private static String sanitize(String input) {
        return input == null ? "" : input.trim();
    }

    public static void validateEmail(String email) throws EmailValidationException {
        email = sanitize(email);
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!Pattern.matches(regex, email)) {
            throw new EmailValidationException("Invalid email format. Example: user@example.com");
        }
    }

    public static void validatePhone(String phone) throws PhoneValidationException {
        phone = sanitize(phone);
        String regex = "^[6-9][0-9]{9}$"; // 10 digits starting with 6-9
        if (!Pattern.matches(regex, phone)) {
            throw new PhoneValidationException("Invalid phone number. Must be 10 digits starting with 6-9.");
        }
    }

    public static void validatePassword(String password) throws PasswordValidationException {
        password = sanitize(password);
        // At least 8 chars, one uppercase, one lowercase, one digit, one special char
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$";
        if (!Pattern.matches(regex, password)) {
            throw new PasswordValidationException(
                "Weak password. Must be 8+ chars with uppercase, lowercase, digit, and special character."
            );
        }
    }

    public static void validateEmployeeId(String empId) throws EmployeeIdValidationException {
        empId = sanitize(empId);
        String regex = "^EMP-[0-9]{4}$"; // EMP-XXXX
        if (!Pattern.matches(regex, empId)) {
            throw new EmployeeIdValidationException("Invalid Employee ID. Format must be EMP-XXXX.");
        }
    }
}
