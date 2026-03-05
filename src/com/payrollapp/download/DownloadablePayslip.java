package com.payrollapp.download;

public final class DownloadablePayslip implements Cloneable {
	private final String empId;
	private final String empName;
	private final String month;
	private final double netPay;

	public DownloadablePayslip(String empId, String empName, String month, double netPay) {
		this.empId = empId;
		this.empName = empName;
		this.month = month;
		this.netPay = netPay;
	}

	public String getEmpId() { return empId; }
	public String getEmpName() { return empName; }
	public String getMonth() { return month; }
	public double getNetPay() { return netPay; }

	@Override
	public Object clone() {
		return new DownloadablePayslip(empId, empName, month, netPay);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof DownloadablePayslip)) return false;
		DownloadablePayslip other = (DownloadablePayslip) o;
		return empId.equals(other.empId) && month.equals(other.month);
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + empId.hashCode();
		result = 31 * result + month.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "PAYSLIP\n"
				+ "Employee ID   : " + empId + "\n"
				+ "Employee Name : " + empName + "\n"
				+ "Month         : " + month + "\n"
				+ "Net Pay       : " + netPay + "\n";
	}
}
