package com.payrollapp.dashboard;

import java.util.*;
import com.payrollapp.payroll.Payslip;
import com.payrollapp.registration.Employee;

public class ManagerDashboard implements Dashboard {
    @Override
    public void display(ArrayList<Payslip> payslips, Employee employee) {
        System.out.println("\n=== MANAGER DASHBOARD ===");
        System.out.println("Manager: " + employee.getName());
        System.out.println("Dashboard Type: " + this.getClass().getName());

        double total = payslips.stream().mapToDouble(Payslip::getNetPay).sum();
        System.out.println("\nTeam Total YTD Earnings: " + total);
    }
}
