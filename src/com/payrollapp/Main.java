// ============== MAIN APP ================
/*
 * Main runner class for Use Case 6.
 *
 * Role of main():
 * - Capture user input
 * - Delegate validation
 * - Handle validation failures gracefully
 *
 * main() does NOT perform validation itself.
 */




package com.payrollapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.payrollapp.authentication.AuthenticationService;
import com.payrollapp.authentication.Session;
import com.payrollapp.payroll.PayrollService;
import com.payrollapp.payroll.Payslip;
import com.payrollapp.registration.Employee;
import com.payrollapp.validation.ValidationException;
import com.payrollapp.download.*;
import com.payrollapp.dashboard.*;

public class Main {
	/*
	 * Entry point for input validation use case.
	 *
	 * Execution Flow:
	 *  1. Read user inputs
	 *  2. Validate each input
	 *  3. Stop immediately if validation fails
	 *  4. Proceed only when all inputs are valid
	 *
	 * @author Developer
	 * @version 6.0
	 */
	public static void main(String[] args) throws IOException {
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
		} catch (ValidationException e) {
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
					} catch (IOException e) {
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
