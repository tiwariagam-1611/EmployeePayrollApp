package com.payrollapp.authentication;

public class PasswordUtil {
	public static String hash(String password) {
		return Integer.toHexString(password.hashCode());
	}
}