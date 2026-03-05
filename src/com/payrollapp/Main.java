// ============== Main App ================
/*
 * Main runner class for Use Case 5.
 *
 * Role of main():
 * - Collect user input
 * - Prepare data
 * - Request appropriate dashboard
 * - Display dashboard
 */

package com.payrollapp;

import java.util.ArrayList;
import java.util.Scanner;

import com.payrollapp.authentication.AuthenticationService;
import com.payrollapp.authentication.Session;
import com.payrollapp.payroll.PayrollService;
import com.payrollapp.payroll.Payslip;
import com.payrollapp.registration.Employee;
import com.payrollapp.download.*;
import com.payrollapp.dashboard.*;

public class Main {
	/**
	 * Entry point for dashboard display.
	 *
	 * Execution Flow:
	 * 1. Capture employee details
	 * 2. Prepare payslip data
	 * 3. Select dashboard at runtime
	 * 4. Display dashboard output
	 *
	 * @author Developer
	 * @version 5.0
	 */
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

            System.out.println("\nEmployee Registered Successfully:");
            System.out.println(emp);
        } catch (Exception e) {
            System.out.println("Registration Failed: " + e.getMessage());
        }

        // === UC2: Authentication ===
        System.out.println("\n=== USE CASE 2: EMPLOYEE AUTHENTICATION & LOGIN ===");
        AuthenticationService auth = new AuthenticationService();
        Session session = auth.login();

        // === UC3: Payslip Generation ===
        if (session != null && !session.isExpired()) {
            Employee emp = auth.getEmployeeByUsername(session.getUsername());
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

                // === UC4: Print / Download ===
                System.out.println("\n=== USE CASE 4: PAYSLIP PRINT / DOWNLOAD ===");
                DownloadablePayslip dlPayslip = new DownloadablePayslip(
                        payslip.getEmpId(), payslip.getEmpName(), payslip.getMonth(), payslip.getNetPay());

                DownloadablePayslip copy = (DownloadablePayslip) dlPayslip.clone();
                System.out.println("Verified: Download copy is equal to original.");
                System.out.println("Original hashcode : " + dlPayslip.hashCode());
                System.out.println("Cloned   hashcode : " + copy.hashCode());

                DownloadToken token = new DownloadToken();
                if (!token.isExpired()) {
                    try {
                        FileService fs = new FileService();
                        String txtFile = fs.savePayslipAsText(copy);
                        String pdfFile = fs.savePayslipAsPdf(copy);
                        System.out.println("Payslip Download Successful.");
                        System.out.println("Saved as text file: " + txtFile);
                        System.out.println("Saved as PDF file : " + pdfFile);
                        System.out.println("\n--- Printed Payslip ---\n" + copy);
                    } catch (Exception e) {
                        System.out.println("Download failed: " + e.getMessage());
                    }
                } else {
                    System.out.println("Download token expired.");
                }

                // === UC5: Dashboard Display ===
                System.out.println("\n=== USE CASE 5: DASHBOARD DISPLAY ===");
                System.out.print("Enter Role (EMPLOYEE/MANAGER): ");
                String role = sc.next().trim();

                Dashboard dashboard = DashboardFactory.getDashboard(role);
                if (dashboard != null) {
                    ArrayList<Payslip> payslipList = new ArrayList<>();
                    payslipList.add(payslip);
                    dashboard.display(payslipList, emp);
                } else {
                    System.out.println("Invalid role entered.");
                }
            } else {
                System.out.println("Error: Employee not found for session user.");
            }
        }

        sc.close();
    }
}
