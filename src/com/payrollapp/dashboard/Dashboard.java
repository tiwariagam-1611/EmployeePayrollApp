package com.payrollapp.dashboard;

import java.util.ArrayList;
import com.payrollapp.payroll.Payslip;
import com.payrollapp.registration.Employee;

public interface Dashboard {
    void display(ArrayList<Payslip> payslips, Employee employee);
}
