package com.payrollapp;

import java.util.Scanner;

import com.payrollapp.authentication.AuthenticationService;
import com.payrollapp.authentication.Session;
import com.payrollapp.payroll.PayrollService;
import com.payrollapp.payroll.Payslip;
import com.payrollapp.registration.Employee;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // === UC1: Registration ===
        System.out.println("=== USE CASE 1: EMPLOYEE REGISTRATION ===");
        try {
            System.out.print("Enter Employee ID (EMP-XXXX): ");
            String empId = sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Email: ");
            String email = sc.nextLine();

            System.out.print("Enter Phone (10 digits starting 6-9): ");
            String phone = sc.nextLine();

            System.out.print("Create Username: ");
            String username = sc.nextLine();

            System.out.print("Create Password: ");
            String password = sc.nextLine();

            Employee emp = new Employee(empId, name, email, phone, username, password);
            emp.persist();

            System.out.println("\n----------------------------------------");
            System.out.println("Employee Registered Successfully:");
            System.out.println(emp);
            System.out.println("\nData persisted in file: employee_data.txt");
            System.out.println("----------------------------------------");

        } catch (Exception e) {
            System.out.println("Registration Failed: " + e.getMessage());
        }

        // === UC2: Authentication ===
        System.out.println("=== USE CASE 2: EMPLOYEE AUTHENTICATION & LOGIN ===");
        AuthenticationService auth = new AuthenticationService();
        Session session = auth.login();

        // === UC3: Payslip Generation ===
        if (session != null && !session.isExpired()) {
            Employee emp = auth.getEmployeeByUsername(session.getUsername()); // will now return a real Employee
            if (emp != null) {
                PayrollService service = new PayrollService();

                System.out.print("Enter Month: ");
                String month = sc.nextLine();
                System.out.print("Enter Basic Salary: ");
                double basic = sc.nextDouble();
                System.out.print("Enter HRA: ");
                double hra = sc.nextDouble();
                System.out.print("Enter DA: ");
                double da = sc.nextDouble();
                System.out.print("Enter Allowances: ");
                double allowances = sc.nextDouble();

                Payslip payslip = service.generatePayslip(emp, month, basic, hra, da, allowances);
                System.out.println(payslip);
            } else {
                System.out.println("Error: Employee not found for session user.");
            }
        }


        sc.close();
    }
}
