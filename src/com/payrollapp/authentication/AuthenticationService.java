package com.payrollapp.authentication;

import com.payrollapp.registration.Employee;
import java.io.*;
import java.util.*;

public class AuthenticationService {
    private Map<String, Employee> employees = new HashMap<>();
    private int maxAttempts = 3;

    public AuthenticationService() {
        loadEmployeesFromFile("employee_data.txt");
    }

    private void loadEmployeesFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    Employee emp = new Employee(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
                    employees.put(parts[4], emp); // username as key
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading employees: " + e.getMessage());
        }
    }

    public Session login() {
        Scanner sc = new Scanner(System.in);
        int attempts = 0;

        while (attempts < maxAttempts) {
            System.out.print("Enter Username: ");
            String username = sc.nextLine().trim();

            System.out.print("Enter Password: ");
            String password = sc.nextLine().trim();

            Employee emp = employees.get(username);
            if (emp != null && emp.getPassword().equals(password)) {
                System.out.println("\nLogin Successful!");
                System.out.println("Role: " + (emp.getEmpId().equals("EMP-0001") ? "MANAGER" : "EMPLOYEE"));
                showDashboard(emp.getEmpId());
                return new Session(username);
            } else {
                attempts++;
                System.out.println("Login Failed! Attempts left: " + (maxAttempts - attempts));
            }
        }
        System.out.println("Maximum login attempts exceeded. Access denied.");
        return null;
    }

    private void showDashboard(String empId) {
        System.out.println("\n======= DASHBOARD =======");
        if (empId.equals("EMP-0001")) {
            System.out.println("Manager Dashboard");
            System.out.println("View Team | Approve Requests");
        } else {
            System.out.println("Employee Dashboard");
            System.out.println("View Payslip | Update Profile");
        }
    }

    public Employee getEmployeeByUsername(String username) {
        return employees.get(username);
    }
}
