/*--------------- Validator Class ----------------

This class is responsible ONLY for checking input correctness.

Why we separate validation:
- Keeps main() clean and readable
- Avoids repeating validation logic

Important idea:
- Validation logic does NOT belong to Employee
- Validation happens BEFORE objects are created
*/

package com.payrollapp.registration;

//Validator Class
class Validator {

 // Validate Email
 public static void validateEmail(String email) throws ValidationException {
     if (email == null || !email.contains("@") || !email.contains(".")) {
         throw new ValidationException("Invalid Email: " + email);
     }
 }

 // Validate Indian Phone Number
 public static void validatePhone(String phone) throws ValidationException {
     if (phone == null || phone.length() != 10) {
         throw new ValidationException("Invalid Phone Number: " + phone);
     }
     char firstDigit = phone.charAt(0);
     if (firstDigit != '6' && firstDigit != '7' && firstDigit != '8' && firstDigit != '9') {
         throw new ValidationException("Phone number must start with 6, 7, 8, or 9: " + phone);
     }
     // Ensure all characters are digits
     for (char c : phone.toCharArray()) {
         if (!Character.isDigit(c)) {
             throw new ValidationException("Phone number must contain only digits: " + phone);
         }
     }
 }

 // Validate Employee ID
 public static void validateEmpId(String empId) throws ValidationException {
     if (empId == null || !empId.matches("EMP-\\d{4}")) {
         throw new ValidationException("Invalid Employee ID: " + empId);
     }
 }
}

