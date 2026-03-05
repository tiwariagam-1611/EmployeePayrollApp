/* ---------------- Custom Exception ----------------

This class represents a validation-related problem.

Why this exists:
- Instead of stopping the program abruptly,
  we clearly communicate what went wrong.

For now, think of this as:
"A special error we throw when input is invalid"
*/

package com.payrollapp.registration;

public class ValidationException extends Exception {
	private static final long serialVersionUID = 1L;

	public ValidationException(String message) {
        super(message);
    }
}

