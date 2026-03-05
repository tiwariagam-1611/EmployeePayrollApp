package com.payrollapp.authentication;
/*
 * ---------------- UserAccount Class ----------------
 *
 * This class represents login-related information.
 *
 * Why this is a separate class:
 * - Employee details and login details are different concerns
 * - Keeps responsibilities small and clear
 *
 * This introduces the idea of COMPOSITION:
 * - An Employee HAS a UserAccount
 */
public class UserAccount {
    private String username;
    private String passwordHash;

    public UserAccount(String username, String password) {
        this.username = username;
        this.passwordHash = PasswordUtil.hash(password);
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String username, String password) {
        return this.username.equals(username) &&
               this.passwordHash.equals(PasswordUtil.hash(password));
    }
}

