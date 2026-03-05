/* ---------------- Custom Exception ----------------

This class represents a validation-related problem.

Why this exists:
- Instead of stopping the program abruptly,
  we clearly communicate what went wrong.

For now, think of this as:
"A special error we throw when input is invalid"
*/

package com.payrollapp.validation;

public class ValidationException extends Exception {
	private static final long serialVersionUID = 1L;

	public ValidationException(String message) {
        super(message);
    }
}

class EmailValidationException extends ValidationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailValidationException(String message) {
        super(message);
    }
}

class PhoneValidationException extends ValidationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PhoneValidationException(String message) {
        super(message);
    }
}

class PasswordValidationException extends ValidationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordValidationException(String message) {
        super(message);
    }
}

class EmployeeIdValidationException extends ValidationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmployeeIdValidationException(String message) {
        super(message);
    }
}

