package com.payrollapp.authentication;

// Abstract User class
abstract class User {
    protected String username;
    protected String passwordHash;
    protected String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.passwordHash = PasswordUtil.hash(password);
        this.role = role;
    }

    // Abstract method forces subclasses to define authentication logic
    public abstract boolean authenticate(String username, String password);

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }
}



