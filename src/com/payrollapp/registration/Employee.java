package com.payrollapp.registration;

import java.io.FileWriter;
import java.io.IOException;

public class Employee {
    private String empId;
    private String name;
    private String email;
    private String phone;
    private String username;
    private String password;

    public Employee(String empId, String name, String email, String phone, String username, String password)
            throws ValidationException {
        Validator.validateEmpId(empId);
        Validator.validateEmail(email);
        Validator.validatePhone(phone);

        this.empId = empId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password; 
    }

    public String getEmpId() { return empId; }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    @Override
    public String toString() {
        return "Employee ID : " + empId + "\n" +
               "Name        : " + name + "\n" +
               "Email       : " + email + "\n" +
               "Phone       : " + phone + "\n" +
               "Username    : " + username;
    }

    public void persist() throws IOException {
        try (FileWriter writer = new FileWriter("employee_data.txt", true)) {
            writer.write(empId + "," + name + "," + email + "," + phone + "," + username + "," + password + "\n");
        }
    }
}
