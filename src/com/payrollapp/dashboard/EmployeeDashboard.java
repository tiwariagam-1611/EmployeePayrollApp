package com.payrollapp.dashboard;

import java.util.*;
import com.payrollapp.payroll.Payslip;
import com.payrollapp.registration.Employee;

public class EmployeeDashboard implements Dashboard {
    @Override
    public void display(ArrayList<Payslip> payslips, Employee employee) {
        System.out.println("\n=== EMPLOYEE DASHBOARD ===");
        System.out.println("Welcome, " + employee.getName());
        System.out.println("Dashboard Type: " + this.getClass().getName());

        // Sort payslips by net pay descending
        payslips.sort(Comparator.comparingDouble(Payslip::getNetPay).reversed());

        // Show top 3
        System.out.println("\nRecent Payslips (Top 3):");
        payslips.stream().limit(3).forEach(p -> 
            System.out.println(p.getMonth() + " : " + p.getNetPay())
        );

        // Year-to-date earnings
        double total = payslips.stream().mapToDouble(Payslip::getNetPay).sum();
        System.out.println("\nYear-To-Date Earnings: " + total);
    }
}
