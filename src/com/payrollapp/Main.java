/*
 * --------------- Main Class ---------------
 *
 * Entry point of Use Case 1.
 *
 * Execution Flow:
 * 1. Take input from user
 * 2. Validate input
 * 3. Create objects
 * 4. Persist data
 * 5. Display confirmation
 *
 * @author Developer
 * @version 1.0
 */

package com.payrollapp;

import java.util.Scanner;

import com.payrollapp.authentication.AuthenticationService;
import com.payrollapp.authentication.Session;
import com.payrollapp.registration.Employee;

public class Main {
	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

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
        
        System.out.println("=== USE CASE 2: EMPLOYEE AUTHENTICATION & LOGIN ===");

        AuthenticationService auth = new AuthenticationService();
        Session session = auth.login();

        if (session != null) {
            System.out.println("\n" + session);
            if (!session.isExpired()) {
                System.out.println("Session active and valid.");
            } else {
                System.out.println("Session expired.");
            }
        }
    }

}

